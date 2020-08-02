package optimizer

import spacePartition.SpacePartition
import spacePartition.SpacePartitionBuilder
import vector.Vector
import vector.Vectorizable

class Optimizer(vectors: List<Vectorizable>, spacePartitionBuilder: SpacePartitionBuilder) {

    private val vectorStruct: SpacePartition = spacePartitionBuilder.build(vectors)

    fun getClosest(v: Vector): List<Vectorizable> {
        return vectorStruct.nearestNeighbor(v)
    }

}
