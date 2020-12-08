package y2015.day09

import helpers.linesFile
import helpers.rFindGroupsDestructured
import java.util.Collections.swap

data class Route(
    val from: String,
    val to: String,
    val distance: Int
)

// Found somewhere in a gist
fun <V> Iterable<V>.permutations(): List<List<V>> {
    val retVal: MutableList<List<V>> = mutableListOf()
    fun generate(k: Int, list: List<V>) {
        if (k == 1) {
            retVal.add(list.toList())
        } else {
            for (i in 0 until k) {
                generate(k - 1, list)
                if (k % 2 == 0) {
                    swap(list, i, k - 1)
                } else {
                    swap(list, 0, k - 1)
                }
            }
        }
    }
    generate(this.count(), this.toList())
    return retVal
}

@ExperimentalStdlibApi
fun main() {
    val cities = mutableSetOf<String>()
    val routes = linesFile("data/2015/09_input.txt").map {
        val (from, to, distance) = it.rFindGroupsDestructured("""(\w+)\s\w+\s(\w+)\s=\s([\d]+)+""")
        cities.add(from)
        cities.add(to)
        Route(from, to, distance.toInt())
    }

    val out = cities.permutations().map { permut ->
        (0..(permut.size - 2)).mapNotNull { permIdx ->
            routes.filter {
                ((it.from == permut[permIdx]) && (it.to == permut[permIdx + 1])) ||
                        ((it.to == permut[permIdx]) && (it.from == permut[permIdx + 1]))
            }.ifEmpty { null }?.map { it.distance }
        }
    }.filter { it.size == cities.size - 1 }.map { it.sumBy { it.sum() } }
    println(out.minOrNull())
    println(out.maxOrNull())
}
