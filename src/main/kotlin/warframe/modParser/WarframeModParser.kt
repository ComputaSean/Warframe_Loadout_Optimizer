package warframe.modParser

import genericGame.modCreator.ModCreator
import vector.EuclidianVector
import vector.Vector

/**
 * Parse all Warframe mods stored in a json file into their respective ModCreators.
 * Note that only warframe mods affecting ability duration, efficiency, range, and strength are of interest.
 */
class WarframeModParser : JSONModParser() {

    override fun isDesired(jsonMod: JSONMod): Boolean {
        if (jsonMod.type != "Warframe Mod" && jsonMod.type != "Warframe") return false
        if (jsonMod.compatName != "WARFRAME") return false
        if (jsonMod.uniqueName.contains("PvP") || jsonMod.uniqueName.contains("Augment")) return false
        if (jsonMod.uniqueName.contains("Beginner")) return false
        if (jsonMod.name == "Energy Conversion") return false
        // Consider only mods that affect the stats of interest
        for (s in jsonMod.levelStats[0].stats) {
            if (s.contains("Ability Duration") || s.contains("Ability Efficiency") ||
                s.contains("Ability Range") || s.contains("Ability Strength")
            ) return true
        }
        return false
    }

    override fun buildModCreator(jsonMod: JSONMod): ModCreator {
        return ModCreator(
            jsonMod.name,
            getFamily(jsonMod.name),
            jsonMod.fusionLimit,
            jsonMod.baseDrain,
            getStats(jsonMod)
        )
    }

    /**
     * Returns an R^4 vector with ordered components representing the stats of [jsonMod].
     * The vector has the following format: [duration, efficiency, range, strength]
     */
    private fun getStats(jsonMod: JSONMod): Vector {
        val stats = DoubleArray(4)
        for (r in jsonMod.levelStats[0].stats) {
            val arr = r.split(",")
            // Iterate through mod's description
            for (s in arr) {
                var index = -1  // Keep track of which component should be updated
                when {
                    s.contains("Ability Duration") -> index = 0
                    s.contains("Ability Efficiency") -> index = 1
                    s.contains("Ability Range") -> index = 2
                    s.contains("Ability Strength") -> index = 3
                }
                // Update vector's component as needed
                if (index != -1) {
                    val tmp = s.split("%")
                    stats[index] += tmp[0].toDouble()
                }
            }
        }
        return EuclidianVector(*stats)
    }

    /**
     * Return the family that [name] is a part of.
     * "Primed x" and "Umbral x" belong to the same family as "x" since they conflict when installed as mods.
     */
    private fun getFamily(name: String): String {
        if (name.contains("Primed") || name.contains("Umbral")) {
            return name.split(" ")[1]
        }
        return name
    }

}
