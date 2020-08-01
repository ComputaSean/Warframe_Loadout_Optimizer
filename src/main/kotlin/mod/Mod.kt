package mod

import vector.Vector

data class Mod(
    val name: String,
    val rank: Byte,
    val drain: Byte,
    val family: String,
    val stats: Vector
)