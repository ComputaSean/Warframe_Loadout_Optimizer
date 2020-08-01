package vector

interface Vector : Iterable<Double>, Vectorizable {

    fun add(v: Vector): Vector
    fun subtract(v: Vector): Vector
    fun scale(factor: Double): Vector
    fun dot(v: Vector): Double
    fun getDirection(): Vector
    fun getDimension(): Int

    operator fun get(index: Int): Double
    operator fun set(index: Int, value: Double)

    operator fun plus(v: Vector): Vector {
        return add(v)
    }

    operator fun minus(v: Vector): Vector {
        return subtract(v)
    }

    operator fun times(c: Double): Vector {
        return scale(c)
    }

    operator fun times(v: Vector): Double {
        return dot(v)
    }

}