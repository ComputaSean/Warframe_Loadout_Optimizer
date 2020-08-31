package genericGame.modManager

import genericGame.modCreator.ModCreatorI

class ModManager {

    private val modCreators = ArrayList<ModCreatorI>()

    fun addModCreator(modCreator: ModCreatorI): Boolean {
        if (modCreators.contains(modCreator)) return false
        modCreators.add(modCreator)
        return true
    }

    fun getModCreator(index: Int): ModCreatorI? {
        return if (index >= 0 && index < modCreators.size)
            modCreators[index]
        else {
            null
        }
    }

    fun removeModCreator(index: Int): ModCreatorI? {
        return if (index >= 0 && index < modCreators.size) {
            modCreators.removeAt(index)
        } else {
            null
        }
    }

    fun clearAll() = modCreators.clear()

    fun getAll(): List<ModCreatorI> {
        return ArrayList(modCreators)
    }

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
