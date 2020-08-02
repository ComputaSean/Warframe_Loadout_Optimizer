package genericGame.modCreator

import genericGame.mod.ModI

interface ModCreatorI {
    val name: String
    val family: String
    val maxRank: Int
    fun getRank(i: Int): ModI
}
