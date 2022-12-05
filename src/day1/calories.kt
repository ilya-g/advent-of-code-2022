package day1

import common.readLines

fun main() {
    val lines = readLines("day1")

    val splitters = listOf(-1) + lines.indices.filter { lines[it].isEmpty() } + lines.size
    val groups = splitters.zipWithNext { p, n -> lines.slice(p + 1..<n).map { it.toInt() } }
    val groupSums = groups.map { it.sum() }

    println(groupSums.max())

    println(groupSums.sortedDescending().take(3).sum())

}