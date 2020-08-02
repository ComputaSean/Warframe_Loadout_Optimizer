package genericGame.loadout

import genericGame.mod.ModI
import vector.Vectorizable

interface LoadoutI : Vectorizable {
    fun addMod(mod: ModI)
    fun removeMod(mod: ModI)
    fun clear()
}
