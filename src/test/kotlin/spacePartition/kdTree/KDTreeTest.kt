package spacePartition.kdTree

import org.junit.jupiter.api.Assertions.assertTrue
import vector.EuclidianVector
import vector.Vector
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow
import kotlin.math.sqrt

internal class KDTreeTest {

    @org.junit.jupiter.api.Test
    fun search100Test() {
        searchTest(100, 4)
    }

    private fun searchTest(numVectors: Int, dimension: Int) {
        val coordinates = generateVectors(numVectors, dimension)
        val tree = KDTree(coordinates, dimension)

        for (v in coordinates) {
            assertTrue(tree.search(v))
        }

        val coordinatesSet = HashSet(coordinates)
        val morePoints = generateVectors(numVectors, dimension)

        for (v in morePoints) {
            if (v in coordinatesSet) assertTrue(tree.search(v))
            else assertTrue(!tree.search(v))
        }
    }

    @org.junit.jupiter.api.Test
    fun nearestNeighbor100Test() {
        nearestNeighborTest(100, 4)
    }

    private fun nearestNeighborTest(numVectors: Int, dimension: Int) {
        val coordinates = generateVectors(numVectors, dimension)
        val findNeighbors = generateVectors(numVectors, dimension)

        val tree = KDTree(coordinates, dimension)

        for (i in findNeighbors.indices) {
            val treeNearest = tree.nearestNeighbor(findNeighbors[i])[0].getVector()
            val bfNearest = bfClosestNeighbor(findNeighbors[i], coordinates)
            assertTrue(getDist(treeNearest, findNeighbors[i]) == getDist(bfNearest, findNeighbors[i]))
        }
    }

    private fun getDist(v1: Vector, v2: Vector): Double {
        var sum = 0.0
        for (i in 0 until v1.getDimension()) {
            sum += ((v1[i] - v2[i]).pow(2.0))
        }
        return sqrt(sum)
    }

    private fun bfClosestNeighbor(target: Vector, vectors: List<Vector>): Vector {
        var closest: Vector? = null
        var closestDist = Double.POSITIVE_INFINITY
        for (i in vectors.indices) {
            val candDist = getDist(target, vectors[i])
            if (candDist < closestDist) {
                closest = vectors[i]
                closestDist = candDist
            }
        }
        return closest!!
    }

    private fun generateVectors(numVectors: Int, dimension: Int): List<Vector> {
        val n = 1000
        val vectors = ArrayList<Vector>()
        for (i in 0 until numVectors) {
            val v = DoubleArray(dimension)
            for (j in 0 until dimension) {
                v[j] = (-n..n).random().toDouble()
            }
            vectors.add(EuclidianVector(*v))
        }
        return vectors
    }

}
