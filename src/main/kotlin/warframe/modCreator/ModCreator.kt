package warframe.modCreator

import genericGame.mod.ModI
import genericGame.modCreator.ModCreatorI
import vector.Vector
import warframe.mod.Mod

class ModCreator(
    override val name: String,
    override val maxRank: Int,
    val baseDrain: Int,
    val baseStats: Vector,
    override val family: String
) : ModCreatorI {

    private val rankedMods: MutableMap<Int, Mod> = HashMap()

    override fun getRank(i: Int): ModI {
        checkValidRank(i)
        if (!rankedMods.containsKey(i)) {
            rankedMods[i] =
                Mod(name, i, baseDrain + i, family, baseStats * (i + 1).toDouble())
        }
        return rankedMods[i]!!
    }

    private fun checkValidRank(rank: Int) {
        if (rank < 0 || rank > maxRank) throw ModCreatorException(
            String.format(
                "Invalid mod rank for %s",
                name
            )
        )
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
