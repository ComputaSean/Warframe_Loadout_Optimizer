package vector

/**
 * An immutable vector that supports iteration over its components.
 */
interface Vector : Iterable<Double>, Vectorizable {

    /**
     * Add [v] to the instance vector and return the new resulting vector.
     */
    fun add(v: Vector): Vector

    /**
     * Subtract [v] from the instance vector and return the new resulting vector.
     */
    fun subtract(v: Vector): Vector

    /**
     * Scale the instance vector by [factor] and return the new resulting vector.
     */
    fun scale(factor: Double): Vector

    /**
     * Dots the instance vector with [v] and return the new resulting vector.
     */
    fun dot(v: Vector): Double

    /**
     * Returns a unit direction vector.
     */
    fun getDirection(): Vector

    /**
     * Returns the dimension of the instance vector.
     */
    fun getDimension(): Int

    /**
     * Support indexing into a vector's component.
     */
    operator fun get(index: Int): Double

    /**
     * Support using the '+' operator for vector addition.
     */
    operator fun plus(v: Vector): Vector {
        return add(v)
    }

    /**
     * Support using the '-' operator for vector subtraction.
     */
    operator fun minus(v: Vector): Vector {
        return subtract(v)
    }

    /**
     * Support using the '*' operator for scalar multiplication.
     */
    operator fun times(c: Double): Vector {
        return scale(c)
    }

    /**
     * Support using the '*' operator for dot product.
     */
    operator fun times(v: Vector): Double {
        return dot(v)
    }

}
