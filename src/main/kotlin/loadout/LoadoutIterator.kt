package loadout

import mod.ModCreator
import vector.Vector

class LoadoutIterator(
    private val mcPool: List<ModCreator>,
    private val maxCapacity: Byte,
    private val numStats: Byte,
    private val baseStats: Vector
) : Iterator<Loadout> {

    private val rankOrder: MutableList<Byte> = ArrayList(mcPool.size)
    private var complete: Int = 0

    init {
        // Loadout must be able to fit all mods
        if (mcPool.size > maxCapacity) {
            complete = 1
        }
        // Check if all the mods are compatible
        else {
            outer@ for (i in mcPool.indices) {
                for (j in i + 1 until mcPool.size) {
                    if (mcPool[i].family == mcPool[j].family) {
                        complete = 1
                        break@outer
                    }
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
                    rankOrder[i] = (rankOrder[i] + 1).toByte()
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

    override fun next(): Loadout {
        val loadout = Loadout(maxCapacity, numStats, baseStats)
        for (i in mcPool.indices) {
            loadout.addMod(mcPool[i].getRank(rankOrder[i]))
        }
        updateRankOrder()
        return loadout
    }

}