package warframe.modParser

import com.google.gson.Gson
import com.google.gson.JsonParser
import genericGame.modCreator.ModCreatorI
import genericGame.modManager.ModManager
import genericGame.modParser.ModParserI
import java.io.BufferedReader
import java.io.FileReader

abstract class JSONModParser(var filePath: String) : ModParserI {

    private val gson = Gson()

    override fun parse(): ModManager {
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

    protected abstract fun isDesired(jsonMod: JSONMod): Boolean

    protected abstract fun buildModCreator(jsonMod: JSONMod): ModCreatorI

}
