package spacePartition

import vector.Vectorizable

interface SpacePartitionBuilder {
    fun build(vectors: List<Vectorizable>): SpacePartition
}