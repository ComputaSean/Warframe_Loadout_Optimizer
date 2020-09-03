package genericGame.loadoutCreator

import genericGame.loadout.Loadout
import genericGame.modCreator.ModCreator
import vector.Vector

/**
 * Exhaustively creates loadouts using mods from [modCreators] by iteration, with sequential loadouts differing only
 * by a single rank for some installed mod.
 *
 * [modCreators] - used to create the mods that are installed
 * [maxCapacity] - max number of installed mods for a created loadout
 * [baseStats] - base stats of a loadout without any installed mods
 */
class LoadoutIterator(
    private val modCreators: List<ModCreator>,
    private val maxCapacity: Int,
    private val baseStats: Vector
) : Iterator<Loadout> {

    /**
     * [rankOrder] - represents the current ranks of the mods in an ordered list
     * [complete] - flag indicating when all possible loadouts have been created
     */
    private val rankOrder: MutableList<Int> = ArrayList(modCreators.size)
    private var complete: Boolean = false

    init {
        // Check if all the mods are compatible
        outer@ for (i in modCreators.indices) {
            for (j in i + 1 until modCreators.size) {
                if (modCreators[i].family == modCreators[j].family) {
                    complete = true
                    break@outer
                }
            }
        }
        if (!complete) {
            // Start all mods at rank 0
            for (i in modCreators.indices) {
                rankOrder.add(0)
            }
        }
    }

    /**
     * Updates mod ranks for the next loadout to be created.
     * This is done by treating the ranks of all installed mods as a counter, and incrementing the least
     * significant digit. Carry occurs when the rank of an installed mod exceeds its maximum rank, causing
     * the rank to roll back to 0 and incrementing the next adjacent rank.
     */
    private fun updateRankOrder() {
        var carry = true    // Keep track of whether there's a carry digit
        for (i in modCreators.size - 1 downTo 0) {
            // Case: carry propagates
            if (rankOrder[i] + 1 > modCreators[i].maxRank) {
                rankOrder[i] = 0
            }
            // Case: carry doesn't propagate so stop
            else {
                rankOrder[i] = rankOrder[i] + 1
                carry = false
                break
            }
        }
        // Case: Overflow occurred on most significant digit
        // Then all loadouts have been created
        if (carry) {
            complete = true
        }
    }

    override fun hasNext(): Boolean {
        return !complete
    }

    override fun next(): Loadout {
        // Create loadout using mods with rank given by [modCreators]
        val loadout = Loadout(maxCapacity, baseStats)
        for (i in modCreators.indices) {
            loadout.addMod(modCreators[i].getRank(rankOrder[i]))
        }
        updateRankOrder()
        return loadout
    }

}
