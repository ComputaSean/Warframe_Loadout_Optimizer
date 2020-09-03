package warframe.modParser

import com.google.gson.Gson
import com.google.gson.JsonParser
import genericGame.modCreator.ModCreator
import genericGame.modManager.ModManager
import genericGame.modParser.ModParser
import java.io.BufferedReader
import java.io.FileReader

/**
 * Parse all mods of interest stored in a json file into their respective ModCreators.
 * The template design pattern is used to allow subclasses to determine which mods should be parsed.
 */
abstract class JSONModParser : ModParser {

    /**
     * [gson] - used in json deserialization
     */
    private val gson = Gson()

    override fun parse(filePath: String): ModManager {
        val modCreatorManager = ModManager()
        val jsonStrArray = JsonParser.parseReader(BufferedReader(FileReader(filePath))).asJsonArray
        for (jsonString in jsonStrArray) {
            val rawMod = gson.fromJson(jsonString, JSONMod::class.java)
            if (isDesired(rawMod)) {
                modCreatorManager.addModCreator(buildModCreator(rawMod))
            }
        }
        return modCreatorManager
    }

    /**
     * Returns whether [jsonMod] is a mod that should be parsed.
     */
    protected abstract fun isDesired(jsonMod: JSONMod): Boolean

    /**
     * Builds the corresponding ModCreator form [jsonMod].
     */
    protected abstract fun buildModCreator(jsonMod: JSONMod): ModCreator

}
