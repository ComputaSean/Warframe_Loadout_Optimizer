package spacePartition.kdTree

import spacePartition.SpacePartition
import spacePartition.SpacePartitionBuilder
import vector.Vectorizable

/**
 * Builder for a KDTree.
 */
class KDTreeBuilder : SpacePartitionBuilder {
    override fun build(vectors: List<Vectorizable>): SpacePartition {
        val dimension = if (vectors.isNotEmpty()) vectors[0].getVector().getDimension() else 0
        return KDTree(vectors, dimension)
    }
}