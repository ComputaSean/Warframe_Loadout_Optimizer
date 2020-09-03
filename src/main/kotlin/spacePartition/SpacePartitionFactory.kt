package spacePartition

import vector.Vectorizable

/**
 * Factory for a Space Partition implementation.
 */
interface SpacePartitionFactory {

    /**
     * Add [vectors] to be used to create the space partition implementation.
     */
    fun addAll(vectors: List<Vectorizable>)

    /**
     * Create and return the space partition implementation.
     */
    fun create(): SpacePartition

}
