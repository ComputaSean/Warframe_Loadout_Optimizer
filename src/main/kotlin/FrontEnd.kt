import genericGame.loadout.Loadout
import genericGame.loadoutCreator.LoadoutCreator
import genericGame.loadoutOptimizer.LoadoutOptimizerFactory
import spacePartition.kdTree.KDTreeFactory
import vector.EuclidianVector
import vector.Vector
import warframe.modParser.WarframeModParser

fun main(args: Array<String>) {

    val baseStats = EuclidianVector(100.0, 100.0, 100.0, 100.0)
    val maxCapacity = 8
    val maxModsInstalled = 4        // 4 mods allows for a reasonable time to create all Loadouts and the KDTree

    val modManager = WarframeModParser().parse("JSON/mods.json")
    val loadoutGenerator = LoadoutCreator(modManager, maxCapacity, baseStats)
    val optimizer = LoadoutOptimizerFactory(loadoutGenerator, maxModsInstalled, KDTreeFactory()).create()

    val pattern = "\\[([+-]?([0-9]*[.])?[0-9]+,\\s*){3}[+-]?([0-9]*[.])?[0-9]+]".toRegex()

    var input: String

    println("Exit by entering nothing.\n")

    do {
        print("Enter desired stats as \"[dur, eff, range, str]\": ")
        input = readLine().toString()
        if (input.isNotEmpty()) {
            if (pattern.matches(input)) {
                val vector = convertToVector(input)
                val loadouts = optimizer.nearestNeighbor(vector) as (MutableList<Loadout>)
                loadouts.sort()
                val closestVector = loadouts[0].getVector()
                if (loadouts.size > 1) print("Loadouts with the closest stats have ")
                else print("The loadout with the closest stats has ")
                println(
                    String.format(
                        "%.2f duration, %.2f efficiency, %.2f range, and %.2f strength.",
                        closestVector[0], closestVector[1], closestVector[2], closestVector[3]
                    )
                )
                var numCurInstalled = -1
                for (loadout in loadouts) {
                    if (loadout.getNumMods() != numCurInstalled) {
                        numCurInstalled = loadout.getNumMods()
                        println(String.format("\tLoadout(s) with %d installed mods:", numCurInstalled))
                    }
                    println("\t\t" + loadout)
                }
            } else println("Invalid input.")
            println()
        }
    } while (input != "")

}

private fun convertToVector(str: String): Vector {
    val numbers = str.split(",").toMutableList()
    numbers[0] = numbers[0].substring(1, numbers[0].lastIndex + 1)
    val lastIndex = numbers.lastIndex
    numbers[lastIndex] = numbers[lastIndex].substring(0, numbers[lastIndex].lastIndex)
    val comps = DoubleArray(numbers.size)
    for (i in comps.indices) {
        comps[i] = numbers[i].trim().toDouble()
    }
    return EuclidianVector(*comps)
}
