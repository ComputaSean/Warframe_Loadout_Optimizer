package genericGame.loadoutCreator

import combination.getAllCombinations
import genericGame.loadout.Loadout
import genericGame.modManager.ModManager
import vector.Vector
import java.util.*

/**
 * Creates loadouts from combinations of mods in [ModManager].
 *
 * [modManager] - contains all usable mods for a loadout
 * [maxCapacity] - max number of mods able to be installed in a loadout
 * [baseStats] - base stats for an unmodded loadout
 */
class LoadoutCreator(
    private val modManager: ModManager,
    private val maxCapacity: Int,
    private val baseStats: Vector
) {

    /**
     * Returns all loadouts that can be created from [modManager] with [numMods] installed.
     */
    fun getAllLoadouts(numMods: Int): List<Loadout> {
        val loadouts = Collections.synchronizedList(ArrayList<Loadout>())
        val modCreatorComb = getAllCombinations(modManager.getAll(), numMods)
        // Add all loadouts in a parallel fashion by assigning each thread a generator to create loadouts
        modCreatorComb.parallelStream().forEach {
            val loadoutIterator = LoadoutGenerator(it, maxCapacity, baseStats)
            while (loadoutIterator.hasNext()) {
                loadouts.add(loadoutIterator.next())
            }
        }
        return loadouts
    }

}
