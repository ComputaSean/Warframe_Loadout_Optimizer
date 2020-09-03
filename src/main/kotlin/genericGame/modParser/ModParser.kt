package genericGame.modParser

import genericGame.modManager.ModManager

/**
 * Parse all mods stored in a file into their respective ModCreators.
 */
interface ModParser {
    /**
     * Convert all mods in [filePath] into ModCreators and return them stored in a ModManager.
     */
    fun parse(filePath: String): ModManager
}
