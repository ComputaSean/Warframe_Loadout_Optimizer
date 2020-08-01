package modParser

import mod.ModCreatorManager

interface ModParser {
    fun parse(): ModCreatorManager
}