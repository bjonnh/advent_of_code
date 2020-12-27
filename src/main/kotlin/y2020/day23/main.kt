package y2020.day23

import kotlin.system.measureTimeMillis

//val cups = "389125467"
val cups = "942387615"

fun part1() {
    var cupList = (cups.map { it.toString().toInt() }).toMutableList()
    var index = 0
    var rounds = 1
    val maxRounds = 100

    while (rounds <= maxRounds) {
        val curValue = cupList[index]

        val taken = (0..2).map { cupList[(it + index + 1) % cupList.size] }

        var destinationExpected = cupList[index] - 1
        var destination = 0
        if (destinationExpected == 0) destinationExpected = cupList.maxOrNull() ?: throw RuntimeException("Never")
        if (destinationExpected in taken) {
            while (true) {
                if (destinationExpected == 0) destinationExpected =
                    cupList.maxOrNull() ?: throw RuntimeException("Darn")
                if (destinationExpected !in taken) break
                destinationExpected--
            }
        }
        destination = cupList.indexOf(destinationExpected)


        val tempList = cupList.map { if (it in taken) -1 else it }.toMutableList()

        (0..2).forEach { tempList.add(destination + it + 1, taken[it]) }

        cupList = tempList.filter { it > 0 }.toMutableList()

        index = (cupList.indexOf(curValue) + 1) % (cupList.size)
        rounds++
    }
    println((0 until cupList.size - 1).map {
        cupList[(cupList.indexOf(1) + it + 1) % (cupList.size)]
    }.joinToString(""))
}

fun part2(input: List<Int>, turns: Int): Array<Int> {
    val map = Array(input.size + 1) { 0 }
    input.forEachIndexed { index, i -> map[i] = input[(index + 1) % input.size] }
    var current: Int = input.first()
    val size = input.size
    repeat(turns) {
        var insertValue = (current - 1).let { if (it < 1) size else it }
        val i = map[current]
        val j = map[i]
        val k = map[j]
        while (insertValue == i || insertValue == j || insertValue == k) {
            insertValue = (insertValue - 1).let { if (it < 1) size else it }
        }
        val cur = map[k]
        val afterInsert = map[insertValue]
        map[k] = afterInsert
        map[current] = cur
        map[insertValue] = i
        current = cur
    }
    return map
}

fun main() {
    part1()
    val time = measureTimeMillis {
        val map = part2((cups.map { it.toString().toInt() }) + (10..1_000_000), 10_000_000)
        println(map[1].let { it.toLong() * map[it] })
    }
    println(time)
}