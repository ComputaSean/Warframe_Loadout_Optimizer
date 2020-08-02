package genericGame.modParser

import genericGame.modManager.ModManager

interface ModParserI {
    fun parse(): ModManager
}
