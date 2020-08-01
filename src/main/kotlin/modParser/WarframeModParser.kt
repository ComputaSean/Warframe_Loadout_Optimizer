package modParser

import mod.ModCreator
import vector.EuclidianVector
import vector.Vector

class WarframeModParser(filePath: String) : JSONModParser(filePath) {

    override fun isDesired(rawMod: RawJSONMod): Boolean {
        if (rawMod.type != "Warframe Mod" && rawMod.type != "Warframe") return false
        if (rawMod.compatName != "WARFRAME") return false
        if (rawMod.uniqueName.contains("PvP") || rawMod.uniqueName.contains("Augment")) return false
        if (rawMod.uniqueName.contains("Beginner")) return false
        if (rawMod.name == "Energy Conversion") return false
        for (s in rawMod.levelStats[0].stats) {
            if (s.contains("Ability Duration") || s.contains("Ability Efficiency") ||
                s.contains("Ability Range") || s.contains("Ability Strength")
            ) return true
        }
        return false
    }

    override fun buildModCreator(rawMod: RawJSONMod): ModCreator {
        return ModCreator(rawMod.name, rawMod.fusionLimit, rawMod.baseDrain, getStats(rawMod), getFamily(rawMod.name))
    }

    private fun getStats(rawMod: RawJSONMod): Vector {
        val baseStats = DoubleArray(4)
        for (r in rawMod.levelStats[0].stats) {
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