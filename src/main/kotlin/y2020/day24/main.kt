package y2020.day24

import java.io.File

typealias Coordinates = Pair<Int, Int>

operator fun Coordinates.plus(direction: Direction): Coordinates = Pair(first + direction.dx, second + direction.dy)
fun Coordinates.neighbors(): List<Coordinates> = Direction.values().map { this + it }

enum class Direction(val dx: Int, val dy: Int) {
    e(2, 0), se(1, 2), ne(1, -2),
    w(-2, 0), sw(-1, 2), nw(-1, -2)
}

fun main() {
    val tiles = mutableMapOf<Coordinates, Boolean>()
    val coords = "([ns]?[ew])".toRegex()
    val input = File("data/2020/24_input.txt").readLines().map {
        coords.findAll(it).map { Direction.valueOf(it.value) }
    }
    input.forEach {
        val key = it.fold(Pair(0, 0)) { acc, direction -> acc + direction }
        tiles[key] = !tiles.getOrDefault(key, false)
    }
    println(tiles.filter { it.value }.size)

    var tilesFix: Map<Coordinates, Boolean> = tiles

    val neighborsCache = mutableMapOf<Coordinates, List<Coordinates>>()
    repeat(100) {
        val changing = tilesFix.flatMap { listOf(it.key) + neighborsCache.getOrPut(it.key) { it.key.neighbors() } }
            .toSet().map { key ->
                val count = neighborsCache.getOrPut(key) { key.neighbors() }.count { tilesFix.getOrDefault(it, false) }
                key to if (tilesFix.getOrDefault(key, false)) !((count == 0) || (count > 2)) else count == 2
            }.toMap()
        tilesFix = changing
    }
    println("Day 100: ${tilesFix.filter { it.value }.size}")
}
