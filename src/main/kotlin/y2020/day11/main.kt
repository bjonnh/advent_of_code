package y2020.day11

import helpers.linesFile
import kotlin.reflect.KFunction3

// 0: Floor
// 1: Occupied
// 2: Free

data class SeatMap(val xMax: Int, val yMax: Int) {
    var map = Array(yMax) { Array<Int>(xMax) { 0 } }
    fun load(data: List<String>) {
        data.forEachIndexed { idx, line ->
            map[idx] = line.map {
                when (it) {
                    '.' -> 0
                    'L' -> 1
                    '#' -> 2
                    else -> throw RuntimeException("Invalid character in input $it")
                }
            }.toTypedArray()
        }
    }

    fun firstInDirection(x: Int, y: Int, xD: Int, yD: Int): Int {
        var xPos = x + xD
        var yPos = y + yD
        while ((xPos in 0 until xMax) && (yPos in 0 until yMax)) {
            if (map[yPos][xPos] > 0) return map[yPos][xPos]
            xPos += xD
            yPos += yD
        }
        return 0 // if we see nothing then it means it is empty
    }

    fun adjacentStatus(x: Int, y: Int): List<Int> = (-1..1).flatMap { xD ->
        (-1..1).map { yD ->
            if (xD == 0 && yD == 0) 0
            else if ((x + xD < 0 || x + xD > xMax - 1) || (y + yD < 0 || y + yD > yMax - 1)) 0 // We are out of the map so not blocking
            else map[y + yD][x + xD] // not blocking if it is empty or if it
        }
    }

    fun adjacentStatus2(x: Int, y: Int): List<Int> = (-1..1).flatMap { xD ->
        (-1..1).map { yD ->
            if (xD == 0 && yD == 0) 0
            else firstInDirection(x, y, xD, yD)
        }
    }

    fun switchingTo(f: KFunction3<SeatMap, Int, Int, List<Int>>, x: Int, y: Int, limit: Int): Int {
        return when (map[y][x]) {
            0 -> 0
            1 -> if (f(this, x, y).all { it < 2 }) 2 else 1 // if all true, we can switch
            2 -> if (f(this, x, y).count { it == 2 } >= limit) 1 else 2
            else -> throw RuntimeException("We don't know that type")
        }
    }

    fun compute(f: KFunction3<SeatMap, Int, Int, List<Int>>, limit: Int) {
        val newMap = map.mapIndexed { y, i ->
            i.mapIndexed { x, value ->
                switchingTo(f, x, y, limit)
            }.toTypedArray()
        }.toTypedArray()
        map = newMap
    }

    fun occupied(): Int = map.sumBy { it.count { it == 2 } }
}

fun main() {
    val seats = linesFile("data/2020/11_input.txt")
    val seatMap = SeatMap(seats[0].length, seats.size)
    seatMap.load(seats)

    var last = -1
    while (last != seatMap.occupied()) {
        last = seatMap.occupied()
        seatMap.compute(SeatMap::adjacentStatus, limit = 4)
    }
    println("Part 1: ${seatMap.occupied()}")

    // reload for part2
    seatMap.load(seats)
    last = -1
    while (last != seatMap.occupied()) {
        last = seatMap.occupied()
        seatMap.compute(SeatMap::adjacentStatus2, limit = 5)
    }
    println("Part 2: ${seatMap.occupied()}")
}