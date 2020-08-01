package modParser

data class RawJSONMod(
    val baseDrain: Byte,
    val compatName: String,
    val fusionLimit: Byte,
    val levelStats: List<LevelStat>,
    val name: String,
    val type: String,
    val uniqueName: String
)

data class LevelStat(
    val stats: List<String>
)
