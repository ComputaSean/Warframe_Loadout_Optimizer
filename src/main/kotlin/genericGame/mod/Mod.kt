package genericGame.mod

import vector.Vector
import vector.Vectorizable

/**
 * A generic ranked mod.
 * Mods typically represent some sort of stat affecting upgrade system in a game, with higher ranked mods being more
 * impactful. A mod can be represented as an component-ordered vector of the stats it affects.
 *
 * [name] - name of the mod
 * [rank] - rank of the mod
 * [drain] - install cost of the mod
 * [family] - family of mods the mod belongs to, used for mod conflicts
 * [stats] - vector whose ordered components represent what stats this mod affects
 */
data class Mod(
    val name: String,
    val rank: Int,
    val drain: Int,
    val family: String,
    val stats: Vector
) : Vectorizable by stats
