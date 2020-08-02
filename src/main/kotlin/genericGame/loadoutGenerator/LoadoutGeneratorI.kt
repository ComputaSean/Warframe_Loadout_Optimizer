package genericGame.loadoutGenerator

import genericGame.loadout.LoadoutI

interface LoadoutGeneratorI {
    fun getAllLoadouts(numMods: Int): List<LoadoutI>
}
