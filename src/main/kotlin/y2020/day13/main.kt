package y2020.day13

import helpers.linesFile

fun main() {
    val lines = linesFile("data/2020/13_input.txt")
    val timeStamp = lines[0].toLong()
    val stuff = lines[1].split(",").filter { it != "x" }.map { it.toLong() }

    val part1 = stuff.map { it to it - (timeStamp % it) }.minByOrNull { it.second }?.let { it.first * it.second }
    println("Part1: $part1")

    var time = 100000000000000L
    val stuff2 = lines[1].split(",").mapIndexed { idx, it -> it to idx }.filter { it.first != "x" }
        .map { it.first.toLong() to it.second.toLong() }
    println(stuff2)
    var step: Long
    var count = 0
    while (true) {
        val vals = stuff2.filter { (it, idx) -> (time + idx) % (it) == 0L }.map { it.first }
        step = vals.fold(1L) { acc, value -> acc * value }
        if (vals.size == stuff2.size) {
            break
        }
        time += step
        count++
    }
    println("Done at $time in $count cycles")
}