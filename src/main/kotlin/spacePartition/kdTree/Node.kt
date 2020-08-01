package spacePartition.kdTree

import vector.Vectorizable

class Node(val elements: List<Vectorizable>, var left: Node?, var right: Node?) {

    val vector = elements[0].getVector()

    fun isLeaf(): Boolean {
        return left == null && right == null
    }

    override fun toString(): String {
        return elements.toString()
    }

}