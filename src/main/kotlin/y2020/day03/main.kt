package y2020.day03

import java.io.File

// File is is src/main/kotlin/y2020/day03

class InfiniteMap(mapSource: List<String>) {
    val map: Array<BooleanArray> = mapSource.mapIndexed { sX, line ->
        line.mapIndexed { sY, item ->
            when (item) {
                '.' -> false
                '#' -> true
                else -> throw RuntimeException("Noo")
            }
        }.toBooleanArray()
    }.toTypedArray()

    val x = mapSource.first().length
    val y = mapSource.size

    fun getThingAt(pX: Int, pY: Int): Boolean = map[pY][pX % x]

    fun slidingCount(dX: Int, dY: Int): Int {
        var pX = 0
        var pY = 0
        var countTrees = 0
        while (pY < y) {
            if (getThingAt(pX, pY)) countTrees++
            pX += dX
            pY += dY
        }
        return countTrees
    }
}


fun main() {
    val mapSource = File("data/2020/day03/input.txt").readLines()
    /*val mapSource = listOf("..##.......",
    "#...#...#..",
    ".#....#..#.",
    "..#.#...#.#",
    ".#...##..#.",
    "..#.##.....",
    ".#.#.#....#",
    ".#........#",
    "#.##...#...",
    "#...##....#",
    ".#..#...#.#")*/
    val map = InfiniteMap(mapSource)

    val part1 = map.slidingCount(3, 1)
    println("Part 1: $part1 trees")

    val part2 = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2)).fold(1) { acc, value ->
        acc * map.slidingCount(value.first, value.second)
    }

    println("Part 2: $part2 trees")
}
