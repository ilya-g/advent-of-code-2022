package day9

import common.positionXY.Pos
import common.readLines
import kotlin.math.abs

data class Move(val dx: Int, val dy: Int)

enum class Direction(val move: Move) {
    R(Move(1, 0)),
    L(Move(-1, 0)),
    U(Move(0, 1)),
    D(Move(0, -1)),
}

operator fun Pos.plus(move: Move): Pos = copy(x + move.dx, y + move.dy)
operator fun Pos.minus(other: Pos): Move = Move(this.x - other.x, this.y - other.y)

// Chebyshev's/Chessboard distance
val Move.distance: Int get() = maxOf(abs(dx), abs(dy))

fun tailToHeadAttraction(head: Pos, tail: Pos): Move {
    val tailToHead = head - tail
    return if (tailToHead.distance > 1) {
        Move(tailToHead.dx.coerceIn(-1, 1), tailToHead.dy.coerceIn(-1, 1))
    } else {
        Move(0, 0)
    }
}

fun main() {
    val input = readLines("day9").map {
        line -> line.split(" ").let { (d, n) -> Direction.valueOf(d) to n.toInt() }
    }

    run part1@ {
        var head = Pos(0, 0)
        var tail = head
        val tailVisited = mutableSetOf(tail)

        for ((d, n) in input) {
            repeat(n) {
                head += d.move
                tail += tailToHeadAttraction(head, tail)
                tailVisited += tail
            }
        }
        println(tailVisited.size)
    }

    run part2@ {
        val knotsNumber = 10
        val knots = MutableList(knotsNumber) { Pos(0, 0) }
        val tailVisited = mutableSetOf(knots.last())

        for ((d, n) in input) {
//            println("$d $n")
            repeat(n) {
                knots[0] = knots[0] + d.move
                for ((headIndex, tailIndex) in knots.indices.zipWithNext()) {
                    knots[tailIndex] = knots[tailIndex] + tailToHeadAttraction(knots[headIndex], knots[tailIndex])
                }
//                println(knots)
                tailVisited += knots.last()
            }
        }
        println(tailVisited.size)
    }

}