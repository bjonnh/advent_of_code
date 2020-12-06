package y2020.day06

import helpers.textFile

fun main() {
    val g = textFile("data/2020/06_input.txt").split("\n\n")
    println(g.sumBy { it.replace("\n", "").toSet().size })
    println(g.sumBy { it.split("\n").map { it.toSet() }.reduce { a, v -> a.intersect(v) }.size })
}