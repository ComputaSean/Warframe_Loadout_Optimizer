package vector

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * An immutable euclidean vector in R^n.
 */
class EuclidianVector(private vararg val comps: Double) : Vector {

    /**Length of the instance vector */
    val magnitude: Double

    init {
        var sum = 0.0
        comps.forEach { sum += it.pow(2.0) }
        magnitude = sqrt(sum)
    }

    override fun add(v: Vector): Vector {
        // Dimension of [v] should be the same as the instance for addition to be valid
        checkSameDimensions(v)
        val newComps = DoubleArray(comps.size)
        for (i in comps.indices)
            newComps[i] = comps[i] + v[i]
        return EuclidianVector(*newComps)
    }

    override fun subtract(v: Vector): Vector {
        // Dimension of [v] should be the same as the instance for subtraction to be valid
        checkSameDimensions(v)
        val newComps = DoubleArray(comps.size)
        for (i in comps.indices)
            newComps[i] = comps[i] - v[i]
        return EuclidianVector(*newComps)
    }

    override fun scale(factor: Double): Vector {
        val newComps = DoubleArray(comps.size)
        for (i in comps.indices)
            newComps[i] = factor * comps[i]
        return EuclidianVector(*newComps)
    }

    override fun dot(v: Vector): Double {
        // Dimension of [v] should be the same as the instance for the dot product to be valid
        checkSameDimensions(v)
        var sum = 0.0
        for (i in comps.indices)
            sum += (comps[i] * v[i])
        return sum
    }

    override fun getDirection(): Vector {
        return scale(1 / magnitude)
    }

    override fun getDimension(): Int {
        return comps.size
    }

    /**
     * Checks if [v] has the same dimension as the instance vector.
     */
    private fun checkSameDimensions(v: Vector) {
        if (v.getDimension() != comps.size) throw VectorException("Vector dimensions aren't the same.")
    }

    /**
     * Support indexing into the instance vector's components.
     *
     * Note that EuclidianVectors are indexed starting from 0.
     */
    override operator fun get(index: Int): Double {
        if (index < 0 || index >= comps.size) throw VectorException("Vector components out of bounds.")
        return comps[index]
    }

    override fun getVector(): Vector {
        return this
    }

    /**
     * Return a row vector string representation of the instance vector.
     */
    override fun toString(): String {
        return comps.contentToString()
    }

    /**
     * [other] is considered equal to the instance vector if they have the same dimension, magnitude, and components.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EuclidianVector

        if (comps.size != other.comps.size) return false
        for (i in comps.indices) {
            if (comps[i] != other.comps[i]) {
                return false
            }
        }
        if (magnitude != other.magnitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = comps.contentHashCode()
        result = 31 * result + magnitude.hashCode()
        return result
    }

    override fun iterator(): Iterator<Double> {
        return EuclidianVectorIterator(this)
    }

    class EuclidianVectorIterator(private val v: EuclidianVector) : Iterator<Double> {
        private var curIndex = 0

        override fun hasNext(): Boolean {
            return curIndex < v.getDimension()
        }

        override fun next(): Double {
            return v[curIndex++]
        }
    }

}
