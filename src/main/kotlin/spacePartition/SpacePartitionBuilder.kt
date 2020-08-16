package spacePartition

import vector.Vectorizable

/**
 * Builder for a Space Partition implementation.
 */
interface SpacePartitionBuilder {

    /**
     * Build the space partition implementation with [vectors].
     */
    fun build(vectors: List<Vectorizable>): SpacePartition

}
