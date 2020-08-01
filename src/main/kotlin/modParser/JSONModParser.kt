package modParser

import com.google.gson.Gson
import com.google.gson.JsonParser
import mod.ModCreator
import mod.ModCreatorManager
import java.io.BufferedReader
import java.io.FileReader

abstract class JSONModParser(var filePath: String) : ModParser {

    private val gson = Gson()

    override fun parse(): ModCreatorManager {
        val modCreatorManager = ModCreatorManager()
        val jsonStrArray = JsonParser.parseReader(BufferedReader(FileReader(filePath))).asJsonArray
        for (jsonString in jsonStrArray) {
            val rawMod = gson.fromJson(jsonString, RawJSONMod::class.java)
            if (isDesired(rawMod)) {
                modCreatorManager.addModCreator(buildModCreator(rawMod))
            }
        }
        return modCreatorManager
    }

    protected abstract fun isDesired(rawMod: RawJSONMod): Boolean

    protected abstract fun buildModCreator(rawMod: RawJSONMod): ModCreator

}