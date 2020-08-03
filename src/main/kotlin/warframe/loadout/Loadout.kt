package warframe.loadout

import genericGame.loadout.LoadoutI
import genericGame.mod.ModI
import vector.Vector

class Loadout(private val maxCapacity: Int, private val baseStats: Vector) : LoadoutI {

    private val mods: MutableList<ModI> = ArrayList()

    override fun addMod(mod: ModI) {
        if (mods.size >= maxCapacity) throw LoadoutException("Maximum amount of mods are already installed.")
        if (checkModConflict(mod)) throw LoadoutException("Trying to add a conflicting mod.")
        mods.add(mod)
    }

    fun addAllMods(mod: List<ModI>) {
        mod.forEach { addMod(it) }
    }

    override fun removeMod(mod: ModI) {
        if (!mods.remove(mod)) throw LoadoutException("Specified mod wasn't installed in this loadout.")
    }

    override fun getDrain(): Int {
        var drain = 0
        mods.forEach { drain += it.drain }
        return drain
    }

    override fun getNumMods(): Int {
        return mods.size
    }

    fun checkModConflict(mod: ModI): Boolean {
        return checkModConflict(mod.family)
    }

    fun checkModConflict(family: String): Boolean {
        mods.forEach { if (it.family == family) return true }
        return false
    }

    override fun clear() {
        mods.clear()
    }

    override fun getVector(): Vector {
        var vector: Vector = baseStats
        mods.forEach { vector += it.getVector() }
        return vector
    }

    override fun toString(): String {
        if (mods.size == 0) {
            return "No mods equipped."
        }
        var summary = ""
        summary += String.format("Total drain of %d. ", getDrain())
        mods.forEach { summary += String.format("%s at rank %d, ", it.name, it.rank) }
        return summary.substring(0, summary.length - 2) + "."
    }

}
