package y2020.day10

import helpers.linesFile

fun main() {
    val lines = linesFile("data/2020/10_input.txt").map { it.toLong() }.sorted()
    val jolts = lines + listOf(lines.last() + 3L)
    var bottom = 0L
    val chain = listOf(0L) + jolts.mapNotNull {
        if (it > bottom && it - bottom <= 3) it.also { bottom = it } else null
    }
    val counts = chain.windowed(2).map { it[1] - it[0] }
    println(counts.count { it == 1L } * counts.count { it == 3L })

    val result = mutableMapOf(0L to 1L)
    println(jolts.map { e -> (1..3).map { result[e - it] ?: 0 }.sum().also { result[e] = it } }.last())
}