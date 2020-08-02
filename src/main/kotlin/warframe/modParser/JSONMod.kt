package warframe.modParser

data class JSONMod(
    val baseDrain: Int,
    val compatName: String,
    val fusionLimit: Int,
    val levelStats: List<LevelStat>,
    val name: String,
    val type: String,
    val uniqueName: String
)

data class LevelStat(
    val stats: List<String>
)
