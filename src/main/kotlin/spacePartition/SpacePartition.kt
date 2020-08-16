package spacePartition

import vector.Vector
import vector.Vectorizable

/**
 * An interface for a space partitioning data type.
 */
interface SpacePartition {

    /**
     * Returns if [target] is contained within this Space Partition instance.
     */
    fun search(target: Vector): Boolean

    /**
     * Returns a list of Vectorizable objects that are representable by the closest vector to [target] in terms of
     * euclidean distance within this Space Partition instance.
     */
    fun nearestNeighbor(target: Vector): List<Vectorizable>

}
