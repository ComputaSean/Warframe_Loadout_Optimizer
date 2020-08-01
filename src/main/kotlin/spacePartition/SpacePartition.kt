package spacePartition

import vector.Vector
import vector.Vectorizable

interface SpacePartition {

    fun search(target: Vector): Boolean
    fun nearestNeighbor(target: Vector): List<Vectorizable>

}