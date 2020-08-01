package mod

import vector.Vector

class ModCreator(
    val name: String,
    val maxRank: Byte,
    val baseDrain: Byte,
    val baseStats: Vector,
    val family: String
) {

    private val rankedMods: MutableMap<Byte, Mod> = HashMap()

    fun getRank(rank: Byte): Mod {
        checkValidRank(rank)
        if (!rankedMods.containsKey(rank)) {
            rankedMods[rank] =
                Mod(name, rank, (baseDrain + rank).toByte(), family, baseStats * (rank + 1).toDouble())
        }
        return rankedMods[rank]!!
    }

    private fun checkValidRank(rank: Byte) {
        if (rank < 0 || rank > maxRank) throw ModCreatorException(String.format("Invalid mod rank for %s", name))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModCreator

        if (name != other.name) return false
        if (maxRank != other.maxRank) return false
        if (baseDrain != other.baseDrain) return false
        if (baseStats != other.baseStats) return false
        if (family != other.family) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + maxRank
        result = 31 * result + baseDrain
        result = 31 * result + baseStats.hashCode()
        result = 31 * result + family.hashCode()
        return result
    }

    override fun toString(): String {
        return name
    }

}
