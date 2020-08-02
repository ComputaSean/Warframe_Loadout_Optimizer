package warframe.modParser

import genericGame.modCreator.ModCreatorI
import vector.EuclidianVector
import vector.Vector
import warframe.modCreator.ModCreator

class WarframeModParser(filePath: String) : JSONModParser(filePath) {

    override fun isDesired(jsonMod: JSONMod): Boolean {
        if (jsonMod.type != "Warframe Mod" && jsonMod.type != "Warframe") return false
        if (jsonMod.compatName != "WARFRAME") return false
        if (jsonMod.uniqueName.contains("PvP") || jsonMod.uniqueName.contains("Augment")) return false
        if (jsonMod.uniqueName.contains("Beginner")) return false
        if (jsonMod.name == "Energy Conversion") return false
        for (s in jsonMod.levelStats[0].stats) {
            if (s.contains("Ability Duration") || s.contains("Ability Efficiency") ||
                s.contains("Ability Range") || s.contains("Ability Strength")
            ) return true
        }
        return false
    }

    override fun buildModCreator(jsonMod: JSONMod): ModCreatorI {
        return ModCreator(
            jsonMod.name,
            jsonMod.fusionLimit,
            jsonMod.baseDrain,
            getStats(jsonMod),
            getFamily(jsonMod.name)
        )
    }

    private fun getStats(jsonMod: JSONMod): Vector {
        val baseStats = DoubleArray(4)
        for (r in jsonMod.levelStats[0].stats) {
            val arr = r.split(",")
            for (s in arr) {
                var index = -1
                when {
                    s.contains("Ability Duration") -> index = 0
                    s.contains("Ability Efficiency") -> index = 1
                    s.contains("Ability Range") -> index = 2
                    s.contains("Ability Strength") -> index = 3
                }
                if (index != -1) {
                    val tmp = s.split("%")
                    baseStats[index] += tmp[0].toDouble()
                }
            }
        }
        return EuclidianVector(*baseStats)
    }

    private fun getFamily(name: String): String {
        if (name.contains("Primed") || name.contains("Umbral")) {
            return name.split(" ")[1]
        }
        return name
    }

}
