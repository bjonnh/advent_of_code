package y2020.day17

import helpers.linesFile

typealias Coordinate = List<Int>

fun Coordinate.vecplus(c: Coordinate) = c.mapIndexed { idx, it -> (this.getOrNull(idx) ?: 0) + it }

data class ConwayND(
    val dimensions: Int
) {
    var content: MutableMap<Coordinate, Boolean> = mutableMapOf()
    var initialList: List<List<Int>> = (-1..1).map { listOf(it) }

    init {
        repeat(dimensions - 1) {
            initialList = initialList.flatMap { oldList -> (-1..1).map { oldList + listOf(it) } }
        }
    }

    fun load(map: Map<Coordinate, Boolean>) {
        map.forEach { content[it.key + (0 until dimensions - it.key.size).map { 0 }] = it.value }
    }

    fun numberOfActiveNeighbors(position: Coordinate): Int =
        actOnNeighbor(position) {
            content[it] ?: false
        }.count { it }

    fun <T> actOnNeighbor(position: Coordinate, f: (Coordinate) -> T): List<T> {
        return initialList.mapNotNull {
            if (it.all { it == 0 }) null
            else f(position.vecplus(it))
        }
    }

    fun cycle() {
        val newMap = mutableMapOf<Coordinate, Boolean>()
        val cache = mutableMapOf<Coordinate, Int>()
        content.forEach { existing ->
            actOnNeighbor(existing.key) {
                val neighs: Int = cache.getOrPut(it) { numberOfActiveNeighbors(it) }
                if ((neighs == 3) or ((content[it] == true) and (neighs == 2)))
                    newMap[it] = true
            }
        }
        content = newMap
    }
}

fun main() {
    val source = linesFile("data/2020/17_input.txt")
    val input = source.flatMapIndexed { y, line -> line.mapIndexed { x, it -> listOf(x, y) to (it == '#') } }.toMap()

    ConwayND(3).also { map ->
        map.load(input)
        repeat(6) { map.cycle() }
        println(map.content.values.count { it })
    }

    ConwayND(4).also { map ->
        map.load(input)
        repeat(6) { map.cycle() }
        println(map.content.values.count { it })
    }
}