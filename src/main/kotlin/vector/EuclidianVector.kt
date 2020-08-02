package vector

import kotlin.math.pow
import kotlin.math.sqrt

class EuclidianVector(private vararg val comps: Double) : Vector {

    val magnitude: Double

    init {
        var sum = 0.0
        comps.forEach { sum += it.pow(2.0) }
        magnitude = sqrt(sum)
    }

    override fun add(v: Vector): Vector {
        checkSameDimensions(v)
        val newComps = DoubleArray(comps.size)
        for (i in comps.indices)
            newComps[i] = comps[i] + v[i]
        return EuclidianVector(*newComps)
    }

    override fun subtract(v: Vector): Vector {
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

    private fun checkSameDimensions(v: Vector) {
        if (v.getDimension() != comps.size) throw VectorException("Vector dimensions aren't the same.")
    }

    override operator fun get(index: Int): Double {
        if (index < 0 || index >= comps.size) throw VectorException("Vector components out of bounds.")
        return comps[index]
    }

    override fun getVector(): Vector {
        return this
    }

    override fun toString(): String {
        return comps.contentToString()
    }

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
