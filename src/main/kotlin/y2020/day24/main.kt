package y2020.day24

import java.io.File

typealias Coordinates = Pair<Int, Int>

operator fun Coordinates.plus(direction: Direction): Coordinates = Pair(first + direction.dx, second + direction.dy)

fun Coordinates.neighbors(): List<Coordinates> = Direction.values().map { this + it }

enum class Direction(val dx: Int, val dy: Int) {
    EAST(2, 0),
    SOUTH_EAST(1, 2),
    SOUTH_WEST(-1, 2),
    WEST(-2, 0),
    NORTH_WEST(-1, -2),
    NORTH_EAST(1, -2)
}

fun main() {
    val tiles = mutableMapOf<Coordinates, Boolean>()
    val input = File("data/2020/24_input.txt").readLines().map {
        val out = mutableListOf<Direction>()
        var mode = ""
        it.forEach { char ->
            when (char) {
                'n' -> mode = "n"
                's' -> mode = "s"
                'e' -> when (mode) {
                    "n" -> {
                        out.add(Direction.NORTH_EAST)
                        mode = ""
                    }
                    "s" -> {
                        out.add(Direction.SOUTH_EAST)
                        mode = ""
                    }
                    else -> out.add(Direction.EAST)
                }
                'w' -> when (mode) {
                    "n" -> {
                        out.add(Direction.NORTH_WEST)
                        mode = ""
                    }
                    "s" -> {
                        out.add(Direction.SOUTH_WEST)
                        mode = ""
                    }
                    else -> out.add(Direction.WEST)
                }
            }
        }
        out
    }
    input.forEach {
        val key = it.fold(Pair(0, 0)) { acc, direction -> acc + direction }
        tiles[key] = !tiles.getOrDefault(key, false)
    }
    println(tiles.filter { it.value }.size)

    var tilesFix: Map<Coordinates, Boolean> = tiles

    val neighborsCache = mutableMapOf<Coordinates, List<Coordinates>>()
    repeat(100) {
        val changing = tilesFix.flatMap {
            listOf(it.key) + neighborsCache.getOrPut(it.key) { it.key.neighbors() }
        }.toSet().map { key ->
            val count = neighborsCache.getOrPut(key) { key.neighbors() }.count { tilesFix.getOrDefault(it, false) }
            val current = tilesFix.getOrDefault(key, false)
            key to if (current) { // black
                !((count == 0) || (count > 2))
            } else { // white
                count == 2
            }
        }.toMap()
        tilesFix = changing
    }
    println("Day 100: ${tilesFix.filter { it.value }.size}")
}
