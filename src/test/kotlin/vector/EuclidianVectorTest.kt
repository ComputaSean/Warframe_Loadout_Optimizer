package vector

import org.junit.jupiter.api.Assertions.*
import kotlin.math.exp
import kotlin.math.sqrt

internal class EuclidianVectorTest {

    @org.junit.jupiter.api.Test
    fun getMagnitude() {
        var v = EuclidianVector(1.0, 2.0, -3.0, 4.0)
        assertEquals(sqrt(30.0), v.magnitude)
        v = EuclidianVector(0.0, 0.0, 0.0, 0.0)
        assertEquals(0.0, v.magnitude)
    }

    @org.junit.jupiter.api.Test
    fun add() {
        var u = EuclidianVector(1.0, 2.0, -3.0, 4.0)
        var v = EuclidianVector(4.0, -3.0, 2.0, 1.0)
        var expected = EuclidianVector(5.0, -1.0, -1.0, 5.0)
        assertEquals(expected, u.add(v))
        assertEquals(expected, v.add(u))
        u = EuclidianVector(-1.0, -2.0, -3.0, -4.0)
        v = EuclidianVector(-4.0, -3.0, -2.0, -1.0)
        expected = EuclidianVector(-5.0, -5.0, -5.0, -5.0)
        assertEquals(expected, u.add(v))
        assertEquals(expected, v.add(u))
        u = EuclidianVector(1.0, 2.0, 3.0, 4.0)
        v = EuclidianVector(0.0, 0.0, 0.0, 0.0)
        expected = EuclidianVector(1.0, 2.0, 3.0, 4.0)
        assertEquals(expected, u.add(v))
        assertEquals(expected, v.add(u))
    }

    @org.junit.jupiter.api.Test
    fun subtract() {
        var u = EuclidianVector(1.0, 2.0, -3.0, 4.0)
        var v = EuclidianVector(4.0, -3.0, 2.0, 1.0)
        var expected = EuclidianVector(-3.0, 5.0, -5.0, 3.0)
        assertEquals(expected, u.subtract(v))
        expected = EuclidianVector(3.0, -5.0, 5.0, -3.0)
        assertEquals(expected, v.subtract(u))
        u = EuclidianVector(-1.0, -2.0, -3.0, -4.0)
        v = EuclidianVector(-4.0, -3.0, -2.0, -1.0)
        expected = EuclidianVector(3.0, 1.0, -1.0, -3.0)
        assertEquals(expected, u.subtract(v))
        expected = EuclidianVector(-3.0, -1.0, 1.0, 3.0)
        assertEquals(expected, v.subtract(u))
        u = EuclidianVector(1.0, 2.0, 3.0, 4.0)
        v = EuclidianVector(0.0, 0.0, 0.0, 0.0)
        expected = EuclidianVector(1.0, 2.0, 3.0, 4.0)
        assertEquals(expected, u.subtract(v))
        expected = EuclidianVector(-1.0, -2.0, -3.0, -4.0)
        assertEquals(expected, v.subtract(u))
    }

    @org.junit.jupiter.api.Test
    fun scale() {
        val v = EuclidianVector(1.0, 2.0, -3.0, 4.0)
        assertEquals(EuclidianVector(0.0, 0.0, 0.0, 0.0), v.scale(0.0))
        assertEquals(EuclidianVector(1.0, 2.0, -3.0, 4.0), v.scale(1.0))
        assertEquals(EuclidianVector(-2.0, -4.0, 6.0, -8.0), v.scale(-2.0))
    }

    @org.junit.jupiter.api.Test
    fun dot() {
        val v = EuclidianVector(1.0, 2.0, -3.0, 4.0)
        val w = EuclidianVector(5.0, -4.0, 0.0, 3.0)
        val expected = 9.0
        assertEquals(expected, v.dot(w))
        assertEquals(expected, w.dot(v))
    }

    @org.junit.jupiter.api.Test
    fun getDirection() {
        val v = EuclidianVector(1.0, 2.0, -3.0, 4.0)
        val denom = 1 / sqrt(30.0)
        assertEquals(EuclidianVector(denom * 1, denom * 2, denom * -3, denom * 4), v.getDirection())
    }

    @org.junit.jupiter.api.Test
    fun getTest() {
        val v = EuclidianVector(1.0, 2.0, -3.0, 4.0)
        val expected = doubleArrayOf(1.0, 2.0, -3.0, 4.0)
        for (i in expected.indices) {
            assertEquals(expected[i], v[i])
        }
    }

    @org.junit.jupiter.api.Test
    fun getDimension() {
        val v = EuclidianVector(1.0, 2.0, -3.0, 4.0)
        assertEquals(4, v.getDimension())
    }

    @org.junit.jupiter.api.Test
    fun testIterator() {
        val comps = doubleArrayOf(1.0, 2.0, -3.0, 4.0)
        val v = EuclidianVector(*comps)
        var index = 0
        for (comp in v) {
            assertEquals(comps[index], comp)
            index += 1
        }
    }

}
