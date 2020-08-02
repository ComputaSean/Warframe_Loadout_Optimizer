package warframe.mod

import genericGame.mod.ModI
import vector.Vector

data class Mod(
    override val name: String,
    override val rank: Int,
    override val drain: Int,
    override val family: String,
    val stats: Vector
) : ModI {
    override fun getVector(): Vector {
        return stats
    }
}
