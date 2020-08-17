package spacePartition

import vector.Vectorizable

/**
 * Factory for a Space Partition implementation.
 */
interface SpacePartitionFactory {

    /**
     * Create the space partition implementation with [vectors].
     */
    fun create(vectors: List<Vectorizable>): SpacePartition

}
