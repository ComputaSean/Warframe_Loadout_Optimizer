package optimizer

import spacePartition.SpacePartition
import spacePartition.SpacePartitionFactory
import vector.Vector
import vector.Vectorizable

class Optimizer(vectors: List<Vectorizable>, spacePartitionFactory: SpacePartitionFactory) {

    private val vectorStruct: SpacePartition = spacePartitionFactory.create(vectors)

    fun getClosest(v: Vector): List<Vectorizable> {
        return vectorStruct.nearestNeighbor(v)
    }

}
