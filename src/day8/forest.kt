package day8

import common.positionXY.Pos
import common.readLines

class Forest(val trees: List<List<Int>>) {
    val height = trees.size
    val width = trees[0].size

    fun tree(pos: Pos): Int = trees[pos.y][pos.x]
    fun tree(x: Int, y: Int): Int = trees[y][x]

    fun allTreePositions() = sequence {
        for (y in 0..<height) {
            for (x in 0..<width) {
                yield(Pos(x, y))
            }
        }
    }

    fun Pos.isBlockedByOtherTrees(): Boolean {
        val h = tree(this)
        return (0..<x).any { tree(it, y) >= h } &&
                (x + 1..<width).any { tree(it, y) >= h } &&
                (0..<y).any { tree(x, it) >= h } &&
                (y + 1..<height).any { tree(x, it) >= h }
    }

    fun visibleTrees() = allTreePositions().count { !it.isBlockedByOtherTrees() }

    fun Pos.scenicScore(): Int {
        val h = tree(this)
        val scoreL = x - ((0..<x).reversed().firstOrNull { tree(it, y) >= h } ?: 0)
        val scoreR = ((x + 1..<width).firstOrNull { tree(it, y) >= h } ?: (width - 1)) - x
        val scoreU = y - ((0..<y).reversed().firstOrNull { tree(x, it) >= h } ?: 0)
        val scoreD = ((y + 1..<height).firstOrNull { tree(x, it) >= h } ?: (height - 1)) - y
        return scoreR * scoreL * scoreU * scoreD
    }

    fun maxScenicScore() = allTreePositions().maxOf { it.scenicScore() }
}

fun main() {
    with(Forest(
        readLines("day8").map { line -> line.map { it.digitToInt() } }
    )) {

        println(visibleTrees())

        println(maxScenicScore())
    }


}