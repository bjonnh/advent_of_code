package y2020.day05

import helpers.linesFile

fun main() {
    val seats = linesFile("data/2020/05_input.txt").map { line ->
        val value = line.map { if (it in setOf('B', 'R')) 1 else 0 }.joinToString("").toInt(2)
        value.shr(3) * 8 + (value.and(7))
    }
    println("Part 1: ${seats.maxOf { it }}")
    val ids = seats.sorted()
    println("Part 2: ${((ids.first()..ids.last()).toSet() - ids.toSet()).first()}")
}