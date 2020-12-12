package y2020.day12

import helpers.linesFile

interface ShipActions {
    var latitude: Int
    var longitude: Int
    fun north(v: Int)
    fun south(v: Int)
    fun west(v: Int)
    fun east(v: Int)
    fun left(v: Int)
    fun right(v: Int)
    fun forward(v: Int)

    fun distance() = Math.abs(latitude) + Math.abs(longitude)
}

data class Ship(
    var facing: Int = 1 // East
) : ShipActions {
    override var latitude: Int = 0
    override var longitude: Int = 0

    override fun north(v: Int) {
        latitude -= v
    }

    override fun south(v: Int) {
        latitude += v
    }

    override fun east(v: Int) {
        longitude -= v
    }

    override fun west(v: Int) {
        longitude += v
    }

    fun rot(shift: Int) {
        facing = Math.floorMod((facing + shift), 4)
    }

    override fun left(v: Int) {
        rot(-v / 90)
    }

    override fun right(v: Int) {
        rot(v / 90)
    }

    override fun forward(v: Int) {
        when (facing) {
            0 -> north(v)
            2 -> south(v)
            3 -> west(v)
            1 -> east(v)
        }
    }
}

data class Ship2(var wpE: Int, var wpN: Int) : ShipActions {
    override var latitude: Int = 0
    override var longitude: Int = 0

    override fun forward(v: Int) {
        latitude += v * wpN
        longitude += v * wpE
    }

    override fun north(v: Int) {
        wpN += v
    }

    override fun south(v: Int) {
        wpN -= v
    }

    override fun east(v: Int) {
        wpE += v
    }

    override fun west(v: Int) {
        wpE -= v
    }

    override fun right(v: Int) {
        repeat(v / 90) {
            val s = wpE
            wpE = wpN
            wpN = -s
        }
    }

    override fun left(v: Int) {
        repeat(v / 90) {
            val s = wpE
            wpE = -wpN
            wpN = s
        }
    }
}

fun main() {
    val orders = linesFile("data/2020/12_input.txt")
    val ship1 = Ship()
    val ship2 = Ship2(10, 1)
    listOf(ship1, ship2).forEach { ship ->
        orders.forEach {
            val v = it.substring(1).toInt()
            when (it[0]) {
                'N' -> ship.north(v)
                'S' -> ship.south(v)
                'E' -> ship.east(v)
                'W' -> ship.west(v)
                'L' -> ship.left(v)
                'R' -> ship.right(v)
                'F' -> ship.forward(v)
            }
        }
    }
    println(ship1.distance())
    println(ship2.distance())
}