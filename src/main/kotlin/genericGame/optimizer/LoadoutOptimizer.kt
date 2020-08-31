package genericGame.optimizer

import genericGame.loadoutGenerator.LoadoutGeneratorI
import optimizer.Optimizer
import spacePartition.SpacePartitionFactory
import vector.Vector
import vector.Vectorizable
import java.util.*

class LoadoutOptimizer(
    loadoutGenerator: LoadoutGeneratorI,
    minModsInstalled: Int,
    maxModsInstalled: Int,
    spacePartitionFactory: SpacePartitionFactory
) {

    private val optimizer: Optimizer

    init {
        val loadouts = ArrayList<Vectorizable>()
        for (i in minModsInstalled..maxModsInstalled) {
            loadouts.addAll(loadoutGenerator.getAllLoadouts(i))
        }
        optimizer = Optimizer(loadouts, spacePartitionFactory)
    }

    constructor(
        loadoutGenerator: LoadoutGeneratorI,
        maxModsInstalled: Int,
        spacePartitionFactory: SpacePartitionFactory
    ) : this(loadoutGenerator, 0, maxModsInstalled, spacePartitionFactory)

    fun getClosest(v: Vector): List<Vectorizable> {
        return optimizer.getClosest(v)
    }

}
