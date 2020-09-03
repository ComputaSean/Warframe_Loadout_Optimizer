package genericGame.modCreator

import genericGame.mod.Mod
import vector.Vector

/**
 * A factory that creates and maintains ranked mods.
 * Since ranked mods with identical properties are effectively the same, created mods come from an
 * object pool to save memory.
 *
 * [name] - name of the created ranked mods
 * [family] - family the created ranked mods belong to
 * [maxRank] - rank of the maximum creatable ranked mod
 * [baseDrain] - drain of a zero-ranked mod
 * [baseStats] - vector whose ordered components represent the base stats of a zero-ranked mod
 */
class ModCreator(
    val name: String,
    val family: String,
    val maxRank: Int,
    val baseDrain: Int,
    val baseStats: Vector
) {

    /**
     * [rankedMods] - object pool of ranked mods
     */
    private val rankedMods: Array<Mod?> = arrayOfNulls(maxRank + 1)

    /**
     * Returns a mod of rank [i] using the ModCreator's properties.
     */
    fun getRank(i: Int): Mod {
        checkValidRank(i)
        if (rankedMods[i] == null) {
            rankedMods[i] = Mod(name, i, baseDrain + i, family, baseStats * (i + 1).toDouble())
        }
        return rankedMods[i]!!
    }

    /**
     * Determines if a valid mod can be created of the given [rank].
     */
    private fun checkValidRank(rank: Int) {
        if (rank < 0 || rank > maxRank) throw ModCreatorException(
            String.format("Invalid mod rank for %s", name)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModCreator

        if (name != other.name) return false
        if (family != other.family) return false
        if (maxRank != other.maxRank) return false
        if (baseDrain != other.baseDrain) return false
        if (baseStats != other.baseStats) return false

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

}