package warframe.loadoutGenerator

import combination.getAllCombinations
import genericGame.loadout.LoadoutI
import genericGame.loadoutGenerator.LoadoutGeneratorI
import genericGame.modManager.ModManager
import vector.Vector
import java.util.*

class LoadoutGenerator(
    private val modManager: ModManager,
    private val maxCapacity: Int,
    private val baseStats: Vector
) : LoadoutGeneratorI {

    override fun getAllLoadouts(numMods: Int): List<LoadoutI> {
        val loadouts = Collections.synchronizedList(ArrayList<LoadoutI>())
        val modCreatorComb = getAllCombinations(modManager.getAll(), numMods)
        modCreatorComb.parallelStream().forEach {
            val loadoutIterator =
                LoadoutIterator(it, maxCapacity, baseStats)
            while (loadoutIterator.hasNext()) {
                loadouts.add(loadoutIterator.next())
            }
        }
        return loadouts
    }

}
