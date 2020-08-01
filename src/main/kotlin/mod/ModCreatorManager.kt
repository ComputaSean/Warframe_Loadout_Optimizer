package mod

import combination.getAllCombinations

class ModCreatorManager {

    private val modCreators: MutableSet<ModCreator> = HashSet()

    fun addModCreator(modCreator: ModCreator) {
        if (modCreators.contains(modCreator))
            throw ModCreatorManagerException("ModCreator already exists in the manager.")
        modCreators.add(modCreator)
    }

    fun removeModCreator(modCreator: ModCreator) {
        if (!modCreators.remove(modCreator))
            throw ModCreatorManagerException("ModCreator doesn't exist in the manager.")
    }

    fun clearAll(modCreator: ModCreator) = modCreators.clear()

    fun getAllCombsOfSize(numModCreators: Int): List<List<ModCreator>> {
        return getAllCombinations(ArrayList(modCreators), numModCreators)
    }

}