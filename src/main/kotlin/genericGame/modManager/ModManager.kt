package genericGame.modManager

import genericGame.modCreator.ModCreator

/**
 * Manages a collection of ModCreator instances.
 */
class ModManager {

    /**
     * [modCreators] - stores all managed ModCreators.
     */
    private val modCreators = ArrayList<ModCreator>()

    /**
     * Adds [modCreator] to be managed and returns whether it is already being managed.
     */
    fun addModCreator(modCreator: ModCreator): Boolean {
        if (modCreators.contains(modCreator)) return false
        modCreators.add(modCreator)
        return true
    }

    /**
     * Returns the ModCreator at [index] from [modCreators], or null if [index] is out of bounds.
     */
    fun getModCreator(index: Int): ModCreator? {
        return if (index >= 0 && index < modCreators.size)
            modCreators[index]
        else {
            null
        }
    }

    /**
     * Removes the ModCreator at [index] from [modCreators], or null if [index] is out of bounds.
     */
    fun removeModCreator(index: Int): ModCreator? {
        return if (index >= 0 && index < modCreators.size) {
            modCreators.removeAt(index)
        } else {
            null
        }
    }

    /**
     * Return all ModCreators being managed.
     */
    fun getAll(): List<ModCreator> {
        return ArrayList(modCreators)
    }

    /**
     * Remove all managed ModCreators.
     */
    fun clearAll() = modCreators.clear()

    /**
     * Return the number of managed ModCreators.
     */
    fun size(): Int {
        return modCreators.size
    }

    override fun toString(): String {
        var out = if (modCreators.size > 0) "" else "None\n"
        for (i in modCreators.indices) {
            out += String.format("%d. %s\n", i + 1, modCreators[i].name)
        }
        return out
    }

}
