package y2020.day12

import helpers.linesFile
import kotlin.math.abs

interface Ship {
    var latitude: Int
    var longitude: Int
    fun north(v: Int)
    fun east(v: Int)
    fun right(v: Int)
    fun forward(v: Int)
    fun distance() = abs(latitude) + abs(longitude)
}

data class Ship1(var facing: Int = 1, override var latitude: Int = 0, override var longitude: Int = 0) : Ship {
    override fun north(v: Int) {
        latitude += v
    }

    override fun east(v: Int) {
        longitude += v
    }

    fun rot(shift: Int) {
        facing = Math.floorMod((facing + shift), 4)
    }

    override fun right(v: Int) = rot(v / 90)

    override fun forward(v: Int) {
        when (facing) {
            0 -> north(v)
            2 -> north(-v)
            3 -> east(-v)
            1 -> east(v)
        }
    }
}

data class Ship2(var wpE: Int, var wpN: Int, override var latitude: Int = 0, override var longitude: Int = 0) : Ship {
    override fun forward(v: Int) {
        latitude += v * wpN
        longitude += v * wpE
    }

    override fun north(v: Int) {
        wpN += v
    }

    override fun east(v: Int) {
        wpE += v
    }

    override fun right(v: Int) = repeat(Math.floorMod(v / 90, 4)) { wpE = wpN.also { wpN = -wpE } }
}

fun main() {
    val orders = linesFile("data/2020/12_input.txt")
    val ship1 = Ship1()
    val ship2 = Ship2(10, 1)
    orders.forEach {
        val v = it.substring(1).toInt()
        listOf(ship1, ship2).forEach { ship ->
            when (it[0]) {
                'N' -> ship.north(v)
                'S' -> ship.north(-v)
                'E' -> ship.east(v)
                'W' -> ship.east(-v)
                'L' -> ship.right(-v)
                'R' -> ship.right(v)
                'F' -> ship.forward(v)
            }
        }
    }
    println("Part1 ${ship1.distance()} Part2 ${ship2.distance()}")
}