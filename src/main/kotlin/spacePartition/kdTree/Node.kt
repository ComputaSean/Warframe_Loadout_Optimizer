package spacePartition.kdTree

import vector.Vectorizable

/**
 * A node of the KDTree.
 *
 * Nodes contain a list of [elements] with the same representative [vector], and a [left] and [right] child.
 */
class Node(val elements: List<Vectorizable>, var left: Node?, var right: Node?) {

    val vector = elements[0].getVector()

    /**
     * Return whether this node instance is a leaf.
     */
    fun isLeaf(): Boolean {
        return left == null && right == null
    }

    override fun toString(): String {
        return elements.toString()
    }

}