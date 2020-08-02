package genericGame.modManager

import genericGame.modCreator.ModCreatorI

class ModManager {

    private val modCreators = HashSet<ModCreatorI>()

    fun addModCreator(modCreator: ModCreatorI) {
        if (modCreators.contains(modCreator))
            throw ModManagerException("ModCreator already exists in the manager.")
        modCreators.add(modCreator)
    }

    fun removeModCreator(modCreator: ModCreatorI) {
        if (!modCreators.remove(modCreator))
            throw ModManagerException("ModCreator doesn't exist in the manager.")
    }

    fun clearAll(modCreator: ModCreatorI) = modCreators.clear()

    fun getAll(): List<ModCreatorI> {
        return ArrayList(modCreators)
    }

}
