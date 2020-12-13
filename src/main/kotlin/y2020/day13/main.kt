package y2020.day13

import helpers.linesFile

fun main() {
    val lines = linesFile("data/2020/13_input.txt")
    val timeStamp = lines[0].toLong()
    val stuff = lines[1].split(",").filter { it != "x" }.map { it.toLong() }

    var time = timeStamp
    while (true) {
        val out = stuff.firstOrNull { time % it == 0L }
        if (out != null) {
            println(out * (time - timeStamp))
            break
        }
        time++
    }

    time = 100000000000000L
    val stuff2 = lines[1].split(",").mapIndexed { idx, it -> it to idx }.filter { it.first != "x" }
        .map { it.first.toLong() to it.second.toLong() }
    var step: Long
    var count = 0
    while (true) {
        val vals = stuff2.filter { (it, idx) -> (time + idx) % (it) == 0L }.map {it.first}
        step = vals.fold(1L) { acc, value -> acc * value }
        if (vals.size == stuff2.size) {
            break
        }
        time += step
        count++
    }
    println("Done at $time in $count cycles")
}