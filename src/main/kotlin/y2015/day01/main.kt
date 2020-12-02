package y2015.day01

import java.io.File

fun main() {
    val input = File("data/2015/01a_input.txt").readText()
    var floor = 0
    input.forEachIndexed { index, action ->
        when (action) {
            '(' -> floor++
            ')' -> floor--
            else -> println("I'm lost here, I'll stay where I am")
        }
        if (floor == -1) {
            println("I'm in the basement after ${index + 1} moves.")
        }
    }
    println("I'm done moving and I'm partying at level $floor")
}