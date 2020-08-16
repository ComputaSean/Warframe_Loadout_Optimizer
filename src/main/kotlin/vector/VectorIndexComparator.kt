package vector

/**
 * Compares the component at index [indexToCompare] between two vector instances.
 */
class VectorIndexComparator(private val indexToCompare: Int) : Comparator<Vector> {
    override fun compare(o1: Vector?, o2: Vector?): Int {
        if (o1 == null || o2 == null) {
            throw NullPointerException()
        }
        return o1[indexToCompare].compareTo(o2[indexToCompare])
    }
}
