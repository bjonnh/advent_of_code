package y2020.day20

import java.io.File
import java.lang.Math.sqrt

/**
 * Take the coordinates in the main map, will multiply by 8 for you and also remove the borders
 */
fun Array<Array<Int>>.fillWithTileAt(x: Int, y: Int, tile: Tile) {
    tile.input.subList(1, 9).mapIndexed { tileX, tileXval ->
        tileXval.subSequence(1, 9).mapIndexed { tileY, tileYval ->
            this[x * 8 + tileX][y * 8 + tileY] = if (tileYval == '#') 1 else 0
        }
    }
}

class Tile(val input: List<String>) {
    val side1: String = input[0]
    val side3: String = input.last()
    val side4: String = input.map { it.first() }.joinToString("")
    val side2: String = input.map { it.last() }.joinToString("")

    fun sides(): List<String> =
        listOf(side1, side2, side3, side4, side1.reversed(), side2.reversed(), side3.reversed(), side4.reversed())


    fun matches(tile: Tile): List<Int> =
        this.sides().mapIndexed { idx, it -> idx to it }
            .filter { sideHere -> tile.sides().any { sideHere.second == it } }
            .map { it.first }

    fun rotate() = Tile((input.indices).map { x ->
        (input.indices).map { y ->
            input[input.size - y - 1][x]
        }.joinToString("")
    })

    fun flipH(): Tile = Tile(input.map { it.reversed() })
    fun flipV(): Tile = Tile(input.reversed())

    fun modifyUntilTrue(f: (Tile) -> Boolean): Tile? {
        var newTile = Tile(input)
        var operation = 0
        while (!f(newTile)) {
            newTile = when (operation) {
                4 -> newTile.flipH()
                9 -> newTile.flipV()
                else -> newTile.rotate()
            }
            operation++
            if (operation > 13) return null // no solution
        }
        return newTile
    }
}

fun main() {
    val data = File("data/2020/20_input.txt").readText().split("\n\n").map {
        val tile = it.split("\n")
        val number = tile[0].split(" ")[1].trim(':')
        number.toInt() to Tile(tile.drop(1).map { it.trim() })
    }.toMap()

    val out = data.flatMap { d1 ->
        data.mapNotNull { d2 ->
            if ((d1 != d2)) {
                val value = d1.value.matches(d2.value)
                if (value.isNotEmpty()) {
                    Pair(Pair(d1.key, d2.key), value)
                } else null
            } else null
        }
    }.toSet()

    val keys = data.keys
    val tileNeighbors = keys.flatMap { thisOne ->
        out.filter { (it.first.first == thisOne) || (it.first.second == thisOne) }.map {
            thisOne to if (it.first.first == thisOne) it.first.second else it.first.first
        }
    }.groupBy { it.first }.map { it.key to it.value.map { it.second }.toSet().toList() }.toMap()
    val corners = tileNeighbors.filter { it.value.size == 2 }
    val part1 = corners.map { it.key }.fold(1L) { acc, value -> acc * value }
    println(part1)

    // Building the map
    val mapSize = sqrt(data.size.toDouble()).toInt()
    val map = Array(mapSize) { Array(mapSize) { 0 } }
    val firstTile = corners.keys.first()
    map[0][0] = firstTile
    map[0][1] = tileNeighbors[firstTile]!![0]
    map[1][0] = tileNeighbors[firstTile]!![1]

    val realMap = Array(mapSize * 8) { Array(mapSize * 8) { 0 } }

    val orientedTile = data[map[0][0]]!!.modifyUntilTrue { newTile ->
        (newTile.side2 in data[map[0][1]]!!.sides()) && (newTile.side3 in data[map[1][0]]!!.sides())
    } ?: throw RuntimeException("Can't find the right orientation")
    realMap.fillWithTileAt(0, 0, orientedTile)

    val origTile2 = data[map[0][1]]!!
    val orientedTile2 = origTile2.modifyUntilTrue { newTile ->
        newTile.side4 == orientedTile.side2
    } ?: throw RuntimeException("Can't find the right orientation")
    realMap.fillWithTileAt(0, 1, orientedTile2)

    val origTile3 = data[map[1][0]]!!
    val orientedTile3 = origTile3.modifyUntilTrue { newTile ->
        newTile.side1 == orientedTile.side3
    } ?: throw RuntimeException("Can't find the right orientation")
    realMap.fillWithTileAt(1, 0, orientedTile3)

    val mapOfOrientations = mutableMapOf(
        map[0][0] to orientedTile,
        map[0][1] to orientedTile2,
        map[1][0] to orientedTile3,
    )
    while (map.any { it.contains(0) }) {
        (0 until mapSize).forEach { x ->
            (0 until mapSize).forEach { y ->
                if (map[x][y] !in mapOfOrientations.keys) {
                    val neighborLeft = if (x > 0) tileNeighbors[map[x - 1][y]] else null
                    val neighborTop = if (y > 0) tileNeighbors[map[x][y - 1]] else null
                    val neighbors = listOf(neighborLeft, neighborTop).filterNotNull()
                    val fnd = neighbors.fold(tileNeighbors.keys.toSet()) { acc, value -> acc.intersect(value) }.minus(
                        map.flatMap { it.map { it } }
                    )
                    if (fnd.size == 1) {
                        map[x][y] = fnd.first()
                        val origTile = data[fnd.first()]!!
                        val newlyOrientedTile = origTile.modifyUntilTrue { newTile ->
                            if (y > 0) {
                                if (map[x][y - 1] != 0 && map[x][y - 1] in mapOfOrientations.keys) {
                                    newTile.side4 == mapOfOrientations[map[x][y - 1]]!!.side2
                                } else false
                            } else if (x > 0) {
                                if (map[x - 1][y] != 0 && map[x - 1][y] in mapOfOrientations.keys) {
                                    newTile.side1 == mapOfOrientations[map[x - 1][y]]!!.side3
                                } else false
                            } else false
                        }
                        if (newlyOrientedTile != null) {
                            mapOfOrientations[fnd.first()] = newlyOrientedTile
                            realMap.fillWithTileAt(x, y, newlyOrientedTile)
                        }
                    }
                }
            }
        }
    }

    val monster = """
                  # 
#    ##    ##    ###
 #  #  #  #  #  #   """.split("\n").drop(1).map { it.map { if (it == '#') 1 else 0 } }

    val snakeMap = Array(mapSize * 8) { Array(mapSize * 8) { 0 } }

    val iter = mapSize * 8

    (0 until iter).map { x -> (0 until iter).map { y -> snakeMap[x][y] = realMap[y][x] } }

    var snakeCount = 0
    (0 until (iter - 20)).forEach { x ->
        (0 until (iter - 3)).forEach { y ->
            val match = (0 until 20).flatMap { mapCoordX ->
                (0 until 3).mapNotNull { mapCoordY ->
                    if (monster[mapCoordY][mapCoordX] == 1) {
                        snakeMap[y + mapCoordY][x + mapCoordX] == 1
                    } else null
                }
            }.all { it }
            if (match) snakeCount++
        }
    }
    println(snakeMap.sumBy { it.count { it == 1 } } - monster.sumBy { it.count { it == 1 } } * snakeCount)
}
