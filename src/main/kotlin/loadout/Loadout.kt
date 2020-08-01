package loadout

import mod.Mod
import vector.Vector
import vector.Vectorizable

class Loadout(private val maxCapacity: Byte, private val numStats: Byte, private val baseStats: Vector) : Vectorizable {

    private val mods: MutableList<Mod> = ArrayList()

    init {
        if (baseStats.getDimension() != numStats.toInt()) {
            throw LoadoutException("Base stats don't match the loadout's number of stats.")
        }
    }

    fun addMod(mod: Mod) {
        if (mods.size >= maxCapacity) throw LoadoutException("Maximum amount of mods are already installed.")
        if (checkModConflict(mod)) throw LoadoutException("Trying to add a conflicting mod.")
        mods.add(mod)
    }

    fun addAllMods(mod: List<Mod>) {
        mod.forEach { addMod(it) }
    }

    fun removeMod(mod: Mod) {
        if (!mods.remove(mod)) throw LoadoutException("Specified mod wasn't installed in this loadout.")
    }

    fun getDrain(): Byte {
        var drain: Byte = 0
        mods.forEach { drain = (drain + it.drain).toByte() }
        return drain
    }

    fun checkModConflict(mod: Mod): Boolean {
        return checkModConflict(mod.family)
    }

    fun checkModConflict(family: String): Boolean {
        mods.forEach { if (it.family == family) return true }
        return false
    }

    fun clearLoadout() {
        mods.clear()
    }

    override fun getVector(): Vector {
        var vector: Vector = baseStats
        mods.forEach { vector += it.stats }
        return vector
    }

    override fun toString(): String {
        if (mods.size == 0) {
            return "No mods equipped."
        }
        var summary = ""
        summary += String.format("Total drain of %d. ", getDrain())
        mods.forEach { summary += String.format("%s at rank %d, ", it.name, it.rank) }
        return summary.substring(0, summary.length - 2)
    }

}