package y2020.day01

import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    var case1: Int? = null
    var case2: Int? = null

    val values: List<Int>
    measureTimeMillis {
        println("Reading data")
        values = File("data/input_puzzle_1.csv").readLines().map { it.toInt() }
    }.let { println("Reading the file took $it ms") }

    val count = measureTimeMillis {
        values.forEach out@{ v1 ->
            values.forEach { v2 ->
                if (case1 == null) {
                    if ((v1 + v2) == 2020) {
                        case1 = v1 * v2
                    }
                }

                if (case2 == null) {
                    values.forEach { v3 ->
                        if ((v1 + v2 + v3) == 2020) {
                            case2 = v1 * v2 * v3
                        }
                    }
                }

                if ((case1 != null) && (case2 != null)) return@out
            }
        }
    }

    println("It took me $count ms")
    println("Solution 1: $case1")
    println("Solution 2: $case2")

    println("Lets run the dumb version")
    mainDumb(values)
}

fun mainDumb(values: List<Int>) {
    var case1: Int? = null
    var case2: Int? = null

    val count = measureTimeMillis {
        // It is fast enough that we don't need to reduce complexity
        // We could have exited the loops as soon as an answer is found
        // We also don't need to do the full permutations 3 times (2000^3)
        // again it took a few ms to find the answer so… not worth it here.

        values.forEach { v1 ->
            values.forEach { v2 ->
                if ((v1 + v2) == 2020) {
                    case1 = v1 * v2
                }

                values.forEach { v3 ->
                    if ((v1 + v2 + v3) == 2020) {
                        case2 = v1 * v2 * v3
                    }
                }
            }
        }
    }

    println("It took me $count ms")
    println("Solution 1: $case1")
    println("Solution 2: $case2")
}