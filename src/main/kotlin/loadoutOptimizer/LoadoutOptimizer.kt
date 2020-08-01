package loadoutOptimizer

import spacePartition.SpacePartition
import loadout.LoadoutIterator
import mod.ModCreatorManager
import vector.Vector
import vector.Vectorizable
import spacePartition.SpacePartitionBuilder
import java.util.*

class LoadoutOptimizer(
    modCreatorManager: ModCreatorManager,
    maxCapacity: Byte,
    private val numStats: Byte,
    baseStats: Vector,
    spacePartitionBuilder: SpacePartitionBuilder
) {

    private val vectorStruct: SpacePartition

    init {
        val loadouts = Collections.synchronizedList(ArrayList<Vectorizable>())
        for (i in 0..maxCapacity) {
            val allCombinations = modCreatorManager.getAllCombsOfSize(i)
            allCombinations.parallelStream().forEach {
                val loadoutIterator =
                    LoadoutIterator(it, maxCapacity, numStats, baseStats)
                while (loadoutIterator.hasNext()) {
                    loadouts.add(loadoutIterator.next())
                }
            }
        }
        vectorStruct = spacePartitionBuilder.build(loadouts)
    }

    fun getClosest(v: Vector): List<Vectorizable> {
        return vectorStruct.nearestNeighbor(v)
    }

}