package combination

fun <T> getAllCombinations(set: List<T>, sampleSize: Int): List<List<T>> {
    val allCombinations: MutableList<MutableList<T>> = ArrayList(choose(set.size, sampleSize))
    getAllCombinations(set, sampleSize, ArrayList(), 0, allCombinations)
    return allCombinations
}

private fun <T> getAllCombinations(
    set: List<T>,
    sampleSize: Int,
    curSelect: MutableList<T>,
    index: Int,
    allCombinations: MutableList<MutableList<T>>
) {

    if (sampleSize == 0) allCombinations += ArrayList(curSelect)
    else {
        for (i in index until set.size) {
            curSelect += set[i]
            getAllCombinations(set, sampleSize - 1, curSelect, i + 1, allCombinations)
            curSelect -= set[i]
        }
    }

}

fun choose(setSize: Int, sampleSize: Int): Int {
    return factorial(setSize) / (factorial(setSize - sampleSize) * factorial(sampleSize))
}

private fun factorial(n: Int): Int {
    var product = 1
    for (i in n downTo 1) {
        product *= i
    }
    return product
}
