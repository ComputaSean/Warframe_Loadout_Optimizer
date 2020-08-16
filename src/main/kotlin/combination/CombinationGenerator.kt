package combination

/**
 * Returns a list of all combinations that can be obtained of size [sampleSize] from [elements].
 */
fun <T> getAllCombinations(elements: List<T>, sampleSize: Int): List<List<T>> {
    // Precalculate list size
    val allCombinations: MutableList<MutableList<T>> = ArrayList(choose(elements.size, sampleSize))
    getAllCombinations(elements, sampleSize, ArrayList(), 0, allCombinations)
    return allCombinations
}

/**
 * Returns all combinations of size [sampleSize] from [elements] starting at the element represented by [index].
 * [curSelect] is the current list of selected elements for the combination, while [allCombinations] is a list
 * of all the [sampleSize] combinations created so far.
 */
private fun <T> getAllCombinations(
    elements: List<T>,
    sampleSize: Int,
    curSelect: MutableList<T>,
    index: Int,
    allCombinations: MutableList<MutableList<T>>
) {

    // Note that if index == [elements].size and sampleSize > 0, then [curSelect] is discarded
    // Hence only combinations of the top level [sampleSize] will be added to [allCombinations]

    // Base Case: No more elements to choose
    if (sampleSize == 0) {
        allCombinations.add(ArrayList(curSelect))
    }

    // Recursive Case: Need to choose more elements
    else {
        // Add each element to the current selection, and increment the index
        // for the recursive call to avoid adding it again.
        for (i in index until elements.size) {
            curSelect += elements[i]
            getAllCombinations(elements, sampleSize - 1, curSelect, i + 1, allCombinations)
            curSelect -= elements[i]
        }
    }

}

/**
 * Returns [setSize]C[sampleSize] in combinatorial notation.
 */
fun choose(setSize: Int, sampleSize: Int): Int {
    return factorial(setSize) / (factorial(setSize - sampleSize) * factorial(sampleSize))
}

/**
 * Returns [n]!.
 */
private fun factorial(n: Int): Int {
    var product = 1
    for (i in n downTo 1) {
        product *= i
    }
    return product
}
