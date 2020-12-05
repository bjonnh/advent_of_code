package y2015.day06

import helpers.linesFile
import helpers.rFindGroupsDestructured

fun Array<BooleanArray>.actOn(x0: Int, y0: Int, x1: Int, y1: Int, f: (Boolean) -> Boolean) {
    for (x in x0..x1) {
        for (y in y0..y1) {
            this[y][x] = f(this[y][x])
        }
    }
}

fun Array<BooleanArray>.count(i: Boolean): Int =
    this.map { it.count { bool -> bool == i } }.sum()

fun Array<IntArray>.actOn(x0: Int, y0: Int, x1: Int, y1: Int, f: (Int) -> Int) {
    for (x in x0..x1) {
        for (y in y0..y1) {
            this[y][x] = f(this[y][x])
        }
    }
}

fun Array<IntArray>.sum(): Int =
    this.map { it.sum() }.sum()


fun main() {
    val array = Array(1000) { BooleanArray(1000) { false } }
    val array2 = Array(1000) { IntArray(1000) { 0 } }

    linesFile("data/2015/05_input.txt").forEach { line ->
        val (command, _, x0s, y0s, _, x1s, y1s) = line.rFindGroupsDestructured("""(turn\son|toggle|turn\soff)\s(([0-9]+),([0-9]+))\sthrough\s(([0-9]+),([0-9]+))""")
            ?: throw RuntimeException("Failing match for $line")
        val (x0, y0, x1, y1) = listOf(x0s.toInt(), y0s.toInt(), x1s.toInt(), y1s.toInt())
        when (command) {
            "turn on" -> {
                array.actOn(x0, y0, x1, y1) {
                    true
                }

                array2.actOn(x0, y0, x1, y1) {
                    it + 1
                }
            }
            "turn off" -> {
                array.actOn(x0, y0, x1, y1) {
                    false
                }

                array2.actOn(x0, y0, x1, y1) {
                    if (it > 0) (it - 1) else 0
                }
            }
            "toggle" -> {
                array.actOn(x0, y0, x1, y1) {
                    it.not()
                }

                array2.actOn(x0, y0, x1, y1) {
                    it + 2
                }
            }
        }
    }
    println("Part 1 ${array.count(true)}")
    println("Part 2 ${array2.sum()}")
}