package genericGame.optimizer

import genericGame.loadoutGenerator.LoadoutGeneratorI
import optimizer.Optimizer
import spacePartition.SpacePartitionBuilder
import vector.Vector
import vector.Vectorizable
import java.util.*

class LoadoutOptimizer(
    loadoutGenerator: LoadoutGeneratorI,
    maxModsInstalled: Int,
    spacePartitionBuilder: SpacePartitionBuilder
) {

    private val optimizer: Optimizer

    init {
        val loadouts = ArrayList<Vectorizable>()
        for (i in 0..maxModsInstalled) {
            loadouts.addAll(loadoutGenerator.getAllLoadouts(i))
        }
        optimizer = Optimizer(loadouts, spacePartitionBuilder)
    }

    fun getClosest(v: Vector): List<Vectorizable> {
        return optimizer.getClosest(v)
    }

}
