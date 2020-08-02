package warframe.loadoutGenerator

import genericGame.loadout.LoadoutI
import genericGame.loadoutGenerator.AbstractLoadoutIterator
import genericGame.modCreator.ModCreatorI
import vector.Vector
import warframe.loadout.Loadout

class LoadoutIterator(
    mcPool: List<ModCreatorI>,
    private val maxCapacity: Int,
    private val baseStats: Vector
) : AbstractLoadoutIterator(mcPool) {

    init {
        // Loadout must be able to fit all mods
        if (mcPool.size > maxCapacity) {
            super.complete = 1
        }
    }

    override fun getNewLoadout(): LoadoutI {
        return Loadout(maxCapacity, baseStats)
    }

}
