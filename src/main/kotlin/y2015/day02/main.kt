import java.io.File

fun main() {
    val input = File("data/2015/02a_input.txt").readLines()
    //val input = listOf("2x3x4", "1x1x10")

    val sides = input.map {
        it.split("x").map { it.toInt() }
    }

    val sidesAreas = sides.map {
        it.mapIndexed { index, value ->
            2 * value * it[(index + 1) % 3]
        }
    }

    val bowNeeded = sides.map {
        val sumOfTwoSmallestSidesTimesTwo = it.sorted().subList(0, 2).sum() * 2
        val bow = it.fold(1) { acc, value ->
            acc * value
        }
        sumOfTwoSmallestSidesTimesTwo + bow
    }.sum()

    val totalSize = sidesAreas.map { sidesArea ->
        sidesArea.sum() + (sidesArea.minOrNull()
            ?: throw RuntimeException("If we couldn't find a minimum something went really wrong")) / 2
    }.sum()

    println("Total length to order $totalSize")
    println("Total bow to order $bowNeeded")
}
