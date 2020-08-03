package genericGame.loadout

import genericGame.mod.ModI
import vector.Vectorizable

interface LoadoutI : Vectorizable, Comparable<LoadoutI> {

    fun addMod(mod: ModI)
    fun removeMod(mod: ModI)
    fun getDrain(): Int
    fun getNumMods(): Int
    fun clear()

    override operator fun compareTo(other: LoadoutI): Int {
        if (getNumMods() != other.getNumMods()) {
            return getNumMods().compareTo(other.getNumMods())
        }
        return getDrain().compareTo(other.getDrain())
    }

}
