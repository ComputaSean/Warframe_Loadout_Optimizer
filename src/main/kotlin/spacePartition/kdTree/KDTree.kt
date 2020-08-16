package spacePartition.kdTree

import spacePartition.SpacePartition
import vector.Vector
import vector.VectorIndexComparator
import vector.Vectorizable
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

class KDTree(vectors: List<Vectorizable>, private val dimension: Int) :
    SpacePartition {

    private val root: Node?

    init {
        val elementMap = mapElements(vectors)
        val keys = ArrayList(elementMap.keys)
        keys.shuffle()
        this.root = constructHelper(keys, elementMap, 0)
    }

    private fun constructHelper(
        keys: List<Vector>?,
        elementMap: Map<Vector, List<Vectorizable>>,
        depth: Int
    ): Node? {

        if (keys == null || keys.isEmpty()) {
            return null
        }

        val splitIndex = depth % dimension
        val sortedKeys = keys.sortedWith(VectorIndexComparator(splitIndex))
        val splitNode =
            Node(
                elementMap[sortedKeys[sortedKeys.size / 2]] ?: error("Vector doesn't exist."), null, null
            )

        val leq = ArrayList<Vector>()
        val gt = ArrayList<Vector>()

        for (i in sortedKeys.indices) {
            if (i == sortedKeys.size / 2) continue
            if (sortedKeys[i][splitIndex] <= splitNode.vector[splitIndex]) leq += sortedKeys[i]
            else gt += sortedKeys[i]
        }

        if (leq.size != 0) splitNode.left = constructHelper(leq, elementMap, depth + 1)
        if (gt.size != 0) splitNode.right = constructHelper(gt, elementMap, depth + 1)

        return splitNode
    }

    private fun mapElements(elements: List<Vectorizable>): MutableMap<Vector, MutableList<Vectorizable>> {
        val elementMap = ConcurrentHashMap<Vector, MutableList<Vectorizable>>()
        elements.parallelStream().forEach {
            elementMap.putIfAbsent(it.getVector(), Collections.synchronizedList(ArrayList()))
            elementMap[it.getVector()]!!.add(it)
        }
        return elementMap
    }

    override fun search(target: Vector): Boolean {
        return searchHelper(target, root, 0)
    }

    private fun searchHelper(target: Vector, curNode: Node?, depth: Int): Boolean {
        if (curNode == null) return false
        if (curNode.vector == target) return true

        val splitIndex = depth % dimension

        return if (target[splitIndex] <= curNode.vector[splitIndex])
            searchHelper(target, curNode.left, depth + 1)
        else
            searchHelper(target, curNode.right, depth + 1)
    }

    override fun nearestNeighbor(target: Vector): List<Vectorizable> {
        return nearestNeighborHelper(target, root, 0)?.elements ?: ArrayList()
    }

    private fun nearestNeighborHelper(target: Vector, curNode: Node?, depth: Int): Node? {
        if (curNode == null) return null
        if (curNode.isLeaf() || curNode.vector == target) return curNode

        var closestNode = curNode
        var closestDist = getDist(closestNode.vector, target)

        val splitIndex = depth % dimension

        val nearestChildNode: Node?
        val otherChild: Node?

        if (target[splitIndex] <= curNode.vector[splitIndex]) {
            nearestChildNode = nearestNeighborHelper(target, closestNode.left, depth + 1)
            otherChild = curNode.right
        } else {
            nearestChildNode = nearestNeighborHelper(target, closestNode.right, depth + 1)
            otherChild = curNode.left
        }

        val nearestChildDist =
            if (nearestChildNode != null) getDist(nearestChildNode.vector, target) else Double.POSITIVE_INFINITY

        if (nearestChildDist < closestDist) {
            closestNode = nearestChildNode
            closestDist = nearestChildDist
        }

        if (closestDist > abs(curNode.vector[splitIndex] - target[splitIndex])) {
            val nearestOtherChildNode = nearestNeighborHelper(target, otherChild, depth + 1)
            val nearestOtherChildDist =
                if (nearestOtherChildNode != null) getDist(nearestOtherChildNode.vector, target)
                else Double.POSITIVE_INFINITY
            if (nearestOtherChildDist < closestDist) {
                closestNode = nearestOtherChildNode
            }
        }

        return closestNode
    }

    private fun getDist(v1: Vector, v2: Vector): Double {
        var sum = 0.0
        for (i in 0 until v1.getDimension()) {
            sum += ((v1[i] - v2[i]).pow(2.0))
        }
        return sqrt(sum)
    }

    fun getHeight(): Int {
        return getHeightHelper(root)
    }

    private fun getHeightHelper(curNode: Node?): Int {
        if (curNode == null || curNode.isLeaf()) return 0
        return max(getHeightHelper(curNode.left), getHeightHelper(curNode.right)) + 1
    }

}