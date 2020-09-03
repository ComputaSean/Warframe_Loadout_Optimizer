package spacePartition.kdTree

import spacePartition.SpacePartitionFactory
import vector.Vectorizable

/**
 * Factory for a KDTree.
 */
class KDTreeFactory : SpacePartitionFactory {

    /** Vectors the KDTree will be built with */
    private val vectors: MutableList<Vectorizable> = ArrayList()

    override fun addAll(vectors: List<Vectorizable>) {
        this.vectors.addAll(vectors)
    }

    override fun create(): KDTree {
        val dimension = if (vectors.isNotEmpty()) vectors[0].getVector().getDimension() else 0
        return KDTree(vectors, dimension)
    }
}