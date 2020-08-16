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

/**
 * A KDTree is a binary tree that partitions space in half at every level with an axis-aligned hyperplane.
 *
 * This implementation supports having repeated vectors in the input list [vectors] and puts them into a single node.
 * The dimension of all the vectors is given by [dimension].
 */
class KDTree(vectors: List<Vectorizable>, private val dimension: Int) : SpacePartition {

    /** root node of the tree */
    private val root: Node?

    init {
        val elementMap = mapElements(vectors)
        val keys = ArrayList(elementMap.keys)
        keys.shuffle()
        this.root = constructHelper(keys, elementMap, 0)
    }

    /**
     * Create a KDTree from [keys] and return the root node.
     *
     * Each level creates a node that splits the space in half with a hyperplane.
     * The splitting vector of the node is chosen as the median of the list of [keys] sorted along a cycling axis.
     * A list of Vectorizable instances with the splitting vector as the key in [elementMap] is stored inside the node.
     * Splitting continues until there are no more [keys].
     * [depth] represents the current depth of this node in the tree's construction.
     */
    private fun constructHelper(
        keys: List<Vector>?,
        elementMap: Map<Vector, List<Vectorizable>>,
        depth: Int
    ): Node? {

        // Base Case: No more keys to split with
        if (keys == null || keys.isEmpty()) {
            return null
        }

        // Calculate the index this node's vector will be split upon
        val splitIndex = depth % dimension
        val sortedKeys = keys.sortedWith(VectorIndexComparator(splitIndex))
        // Create splitting node
        val splitNode =
            Node(elementMap[sortedKeys[sortedKeys.size / 2]] ?: error("Invalid key"), null, null)

        val leq = ArrayList<Vector>()   // Vectors stored on one side of the splitting plane
        val gt = ArrayList<Vector>()    // Vectors stored on the other side

        // Sort [keys] into one of two halves
        for (i in sortedKeys.indices) {
            if (i == sortedKeys.size / 2) continue  // Skip splitting vector
            if (sortedKeys[i][splitIndex] <= splitNode.vector[splitIndex]) leq += sortedKeys[i]
            else gt += sortedKeys[i]
        }

        // Continue to partition halves of space if they contain vectors
        if (leq.size != 0) splitNode.left = constructHelper(leq, elementMap, depth + 1)
        if (gt.size != 0) splitNode.right = constructHelper(gt, elementMap, depth + 1)

        return splitNode
    }

    /**
     * Returns a map of [elements] with a vector as a key and a list of all Vectorizable instances
     * with that key as the value.
     */
    private fun mapElements(elements: List<Vectorizable>): MutableMap<Vector, MutableList<Vectorizable>> {
        // Multiple threads access the critical section when adding an element
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

    /**
     * Return if [target] is in the KDTree rooted at [curNode].
     * [depth] represents the depth of [curNode] with respect to the root node of this KDTree instance.
     */
    private fun searchHelper(target: Vector, curNode: Node?, depth: Int): Boolean {
        // Base Case: No more nodes to search so [target] is not in the tree
        if (curNode == null) return false
        // Base Case: Found [target]
        if (curNode.vector == target) return true

        // Calculate the index this node's vector was split upon
        val splitIndex = depth % dimension

        // Search in the half that [target] would belong to if it is in the KDTree
        return if (target[splitIndex] <= curNode.vector[splitIndex]) {
            searchHelper(target, curNode.left, depth + 1)
        } else {
            searchHelper(target, curNode.right, depth + 1)
        }
    }

    override fun nearestNeighbor(target: Vector): List<Vectorizable> {
        return nearestNeighborHelper(target, root, 0)?.elements ?: ArrayList()
    }

    /**
     * Return the node that's the closest neighbor to [target] in the KDTree rooted at [curNode].
     * [depth] represents the depth of [curNode] with respect to the root node of this KDTree instance.
     */
    private fun nearestNeighborHelper(target: Vector, curNode: Node?, depth: Int): Node? {
        // Base Case: No nodes to search
        if (curNode == null) return null
        // Base Case: Leaf node so no more nodes to search
        if (curNode.isLeaf() || curNode.vector == target) return curNode

        // Recursive Case

        // Set the closest node found to the only one seen so far
        var closestNode = curNode
        var closestDist = getDist(closestNode.vector, target)

        // Calculate the index this node's vector was split upon
        val splitIndex = depth % dimension

        val nearestChildNode: Node?
        val otherChild: Node?

        // Search for the nearest neighbor in the child branch of space that [target] would belong to
        if (target[splitIndex] <= curNode.vector[splitIndex]) {
            nearestChildNode = nearestNeighborHelper(target, closestNode.left, depth + 1)
            otherChild = curNode.right
        } else {
            nearestChildNode = nearestNeighborHelper(target, closestNode.right, depth + 1)
            otherChild = curNode.left
        }

        val nearestChildDist =
            if (nearestChildNode != null) getDist(nearestChildNode.vector, target) else Double.POSITIVE_INFINITY

        // Update the closest node if the node from the child branch is closer to the target
        if (nearestChildDist < closestDist) {
            closestNode = nearestChildNode
            closestDist = nearestChildDist
        }

        // Search the other child branch if the hypersphere centered around [target] with radius [closestDist]
        // intersects with the splitting hyperplane of [curNode]. This is because a closer neighbor may
        // exist in the other child branch than the current closet node.
        if (closestDist > abs(curNode.vector[splitIndex] - target[splitIndex])) {
            val nearestOtherChildNode = nearestNeighborHelper(target, otherChild, depth + 1)
            val nearestOtherChildDist =
                if (nearestOtherChildNode != null) getDist(nearestOtherChildNode.vector, target)
                else Double.POSITIVE_INFINITY
            // Update the closest node if the node from the other child branch is closer
            if (nearestOtherChildDist < closestDist) {
                closestNode = nearestOtherChildNode
            }
        }

        return closestNode
    }

    /**
     * Return the euclidean distance between [v1] and [v2].
     */
    private fun getDist(v1: Vector, v2: Vector): Double {
        var sum = 0.0
        for (i in 0 until v1.getDimension()) {
            sum += ((v1[i] - v2[i]).pow(2.0))
        }
        return sqrt(sum)
    }

    /**
     * Return the height of this KDTree instance.
     */
    fun getHeight(): Int {
        return getHeightHelper(root)
    }

    /**
     * Return the height of the KDTree rooted at [curNode].
     */
    private fun getHeightHelper(curNode: Node?): Int {
        if (curNode == null || curNode.isLeaf()) return 0
        return max(getHeightHelper(curNode.left), getHeightHelper(curNode.right)) + 1
    }

}