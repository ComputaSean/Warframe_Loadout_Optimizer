package genericGame.loadout

import genericGame.mod.Mod
import vector.Vector
import vector.Vectorizable

/**
 * A configurable loadout with base stats that can be changed by installing mods.
 * Loadouts are represented by a vector consisting of the sum of all installed mod vectors and their base stat.
 *
 * [maxCapacity] - the maximum number of mods that can be installed
 * [baseStats] - represents the base stats of the loadout
 */
class Loadout(private val maxCapacity: Int, private val baseStats: Vector) : Vectorizable, Comparable<Loadout> {

    /**
     * [mods] - the installed mods of a loadout
     */
    private val mods: MutableList<Mod> = ArrayList()

    /**
     * Add [mod] to the loadout if possible. Note that this will throw an exception if:
     *      a) the maximum amount of mods for this loadout has already been installed
     *      b) [mod] conflicts with a previously installed mod
     */
    fun addMod(mod: Mod) {
        if (mods.size >= maxCapacity) throw LoadoutException("Maximum amount of mods are already installed.")
        if (checkModConflict(mod)) throw LoadoutException("Trying to add a conflicting mod.")
        mods.add(mod)
    }

    /**
     * Adds every mod from [mods] to the loadout.
     */
    fun addAllMods(mods: List<Mod>) {
        mods.forEach { addMod(it) }
    }

    /**
     * Removes [mod] from the loadout if installed.
     */
    fun removeMod(mod: Mod) {
        if (!mods.remove(mod)) throw LoadoutException("Specified mod wasn't installed in this loadout.")
    }

    /**
     * Clears the loadout of all installed mods.
     */
    fun clear() {
        mods.clear()
    }

    /**
     * Returns the total cost of all installed mods.
     */
    fun getDrain(): Int {
        var drain = 0
        mods.forEach { drain += it.drain }
        return drain
    }

    /**
     * Returns the number of installed mods in the loadout.
     */
    fun getNumMods(): Int {
        return mods.size
    }

    /**
     * Returns if [mod] conflicts with any previously installed mods.
     */
    fun checkModConflict(mod: Mod): Boolean {
        return checkModConflict(mod.family)
    }

    /**
     * Returns if a mod from [family] conflicts with any previously installed mods.
     * Mods of the same family are considered to conflict.
     */
    fun checkModConflict(family: String): Boolean {
        mods.forEach { if (it.family == family) return true }
        return false
    }

    /**
     * Returns the vector representation of the loadout.
     * This is the sum of the vectors of all installed mods and the loadout's base stats vector.
     */
    override fun getVector(): Vector {
        var vector: Vector = baseStats
        mods.forEach { vector += it.getVector() }
        return vector
    }

    /**
     * Imposes an ordering between [other] and this loadout based on their respective drains.
     */
    override fun compareTo(other: Loadout): Int {
        return getDrain().compareTo(other.getDrain())
    }

    override fun toString(): String {
        if (mods.size == 0)
            return "No mods equipped."
        var summary = ""
        summary += String.format("Total drain of %d. ", getDrain())
        mods.forEach { summary += String.format("%s at rank %d, ", it.name, it.rank) }
        return summary.substring(0, summary.length - 2) + "."
    }

}
