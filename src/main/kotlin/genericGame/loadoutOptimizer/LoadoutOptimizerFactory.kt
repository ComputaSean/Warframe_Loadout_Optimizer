package genericGame.loadoutOptimizer

import genericGame.loadoutCreator.LoadoutGenerator
import spacePartition.SpacePartition
import spacePartition.SpacePartitionFactory

/**
 * Creates an optimizer for loadouts from [loadoutGenerator] with between [minMods] and [maxMods] mods installed.
 * An optimizer is a nearest neighbor calculator for a list of vectors, which in this case are the created loadouts.
 *
 * [loadoutGenerator] - creates loadouts of a specified size
 * [minMods] - minimum number of installed mods for a loadout
 * [maxMods] - maximum number of installed mods for a loadout
 * [spacePartitionFactory] - creates the resulting optimzer
 */
class LoadoutOptimizerFactory(
    private val loadoutGenerator: LoadoutGenerator,
    private val minMods: Int,
    private val maxMods: Int,
    private val spacePartitionFactory: SpacePartitionFactory
) {

    /**
     * Alternative constructor for when loadouts with 0-[maxModsInstalled] mods should be considered.
     */
    constructor(
        loadoutGenerator: LoadoutGenerator,
        maxModsInstalled: Int,
        spacePartitionFactory: SpacePartitionFactory
    ) : this(loadoutGenerator, 0, maxModsInstalled, spacePartitionFactory)

    /**
     * Creates and returns a space partition data structure of vectors in the form of loadouts with between
     * [minMods] and [maxMods] installed.
     */
    fun create(): SpacePartition {
        // Add all loadouts from loadoutGenerator with the specified number of mods installed
        for (i in minMods..maxMods) {
            spacePartitionFactory.addAll(loadoutGenerator.getAllLoadouts(i))
        }
        return spacePartitionFactory.create()
    }

}
