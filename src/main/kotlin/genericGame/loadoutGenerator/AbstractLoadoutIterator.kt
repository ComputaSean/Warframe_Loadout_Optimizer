package genericGame.loadoutGenerator

import genericGame.loadout.LoadoutI
import genericGame.modCreator.ModCreatorI

abstract class AbstractLoadoutIterator(private val mcPool: List<ModCreatorI>) : Iterator<LoadoutI> {

    private val rankOrder: MutableList<Int> = ArrayList(mcPool.size)
    protected var complete: Int = 0

    init {
        // Check if all the mods are compatible
        outer@ for (i in mcPool.indices) {
            for (j in i + 1 until mcPool.size) {
                if (mcPool[i].family == mcPool[j].family) {
                    complete = 1
                    break@outer
                }
            }
        }
        if (complete == 0) {
            for (i in mcPool.indices) {
                rankOrder += 0
            }
        }
    }

    private fun updateRankOrder() {
        var carry = true
        for (i in mcPool.size - 1 downTo 0) {
            if (carry) {
                if (rankOrder[i] + 1 > mcPool[i].maxRank) {
                    rankOrder[i] = 0
                } else {
                    rankOrder[i] = rankOrder[i] + 1
                    carry = false
                    break
                }
            }
        }
        if (carry) {
            complete += 1
        }
    }

    override fun hasNext(): Boolean {
        return complete < 1
    }

    override fun next(): LoadoutI {
        val loadout = getNewLoadout()
        for (i in mcPool.indices) {
            loadout.addMod(mcPool[i].getRank(rankOrder[i]))
        }
        updateRankOrder()
        return loadout
    }

    protected abstract fun getNewLoadout(): LoadoutI

}
