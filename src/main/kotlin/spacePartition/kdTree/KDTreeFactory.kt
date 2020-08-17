package spacePartition.kdTree

import spacePartition.SpacePartition
import spacePartition.SpacePartitionFactory
import vector.Vectorizable

/**
 * Factory for a KDTree.
 */
class KDTreeFactory : SpacePartitionFactory {
    override fun create(vectors: List<Vectorizable>): SpacePartition {
        val dimension = if (vectors.isNotEmpty()) vectors[0].getVector().getDimension() else 0
        return KDTree(vectors, dimension)
    }
}