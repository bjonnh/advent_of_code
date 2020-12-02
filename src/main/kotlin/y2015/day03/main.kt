package y2015.day03

import java.io.File


class HouseVisit {
    val visitedHouses = mutableMapOf<Pair<Int, Int>, Int>()
    var currentX = 0
    var currentY = 0

    init {
        visit()
    }

    fun visit() {
        visitedHouses[Pair(currentX, currentY)] = (visitedHouses[Pair(currentX, currentY)] ?: 0) + 1
    }

    fun up() {
        currentY -= 1
        visit()
    }

    fun down() {
        currentY += 1
        visit()
    }

    fun left() {
        currentX -= 1
        visit()
    }

    fun right() {
        currentX += 1
        visit()
    }
}

data class Position(
    var x: Int,
    var y: Int
)

class HouseVisitWithRobot {
    val visitedHouses = mutableMapOf<Pair<Int, Int>, Int>()

    val currentPosition: Position
        get() =
            if (santaWorking) santaPosition else robotPosition


    val santaPosition = Position(0, 0)
    val robotPosition = Position(0, 0)

    var santaWorking = true

    init {
        visitedHouses[Pair(0, 0)] = 1
    }

    fun visit() {
        visitedHouses[Pair(currentPosition.x, currentPosition.y)] =
            (visitedHouses[Pair(currentPosition.x, currentPosition.y)] ?: 0) + 1
        santaWorking = !santaWorking
    }

    fun up() {
        currentPosition.y -= 1
        visit()
    }

    fun down() {
        currentPosition.y += 1
        visit()
    }

    fun left() {
        currentPosition.x -= 1
        visit()
    }

    fun right() {
        currentPosition.x += 1
        visit()
    }
}

fun part1() {

    val houseVisit = HouseVisit()
    File("data/2015/03a_input.txt").readText().forEach {
        when (it) {
            '^' -> houseVisit.up()
            'v' -> houseVisit.down()
            '<' -> houseVisit.left()
            '>' -> houseVisit.right()
        }
    }

    val atLeastOne = houseVisit.visitedHouses.values.count { it > 0 }
    println("Houses that received at least one: $atLeastOne")
}

fun part2() {
    val houseVisit = HouseVisitWithRobot()
    File("data/2015/03a_input.txt").readText().forEach {
        when (it) {
            '^' -> houseVisit.up()
            'v' -> houseVisit.down()
            '<' -> houseVisit.left()
            '>' -> houseVisit.right()
        }
    }

    val atLeastOne = houseVisit.visitedHouses.values.count { it > 0 }
    println("Houses that received at least one with robot: $atLeastOne")
}

fun main() {
    part1()
    part2()
}