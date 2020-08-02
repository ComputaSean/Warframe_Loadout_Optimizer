package genericGame.mod

import vector.Vectorizable

interface ModI : Vectorizable {
    val name: String
    val rank: Int
    val family: String
    val drain: Int
}
