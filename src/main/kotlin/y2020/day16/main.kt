package y2020.day16

import helpers.textFile

fun List<Int>.isValidTicked(rules: List<Pair<String, List<IntRange>>>): List<Int> {
    return this.mapNotNull { value ->
        if (!rules.any {
                it.second.any {
                    it.contains(value)
                }
            }) value else null
    }
}

fun main() {
    val (rulesRaw, ticketRaw, nearbiesRaw) = textFile("data/2020/16_input.txt").split("\n\n")
    val rules: List<Pair<String, List<IntRange>>> = rulesRaw.split("\n").map {
        it.substringBefore(':') to
                (it.substringAfter(':').trim().split(" or ").map {
                    val (first, second) = it.split('-').map {
                        it.toInt()
                    }
                    IntRange(first, second)
                }
                        )

    }
    val ticket = ticketRaw.split("\n")[1].split(",").map { it.toInt() }
    val nearbies = nearbiesRaw.split("\n").drop(1).map { it.split(",").map { it.toInt() } }
    println(rules)
    println(ticket)
    println(nearbies)
    println(nearbies.map { it.isValidTicked(rules) }.flatten().sum())
    // now we look for which can be which
    val validTickets = nearbies.filter { it.isValidTicked(rules).isEmpty() }
    val solved = mutableMapOf<Int, Pair<String, List<IntRange>>>()
    while (solved.size < rules.size) {
        rules.map { filteredRule ->
            filteredRule to (0 until validTickets[0].size).filter { it !in solved.keys }.mapNotNull { pos ->
                if (validTickets.map { listOf(it[pos]) }.all { it.isValidTicked(listOf(filteredRule)).isEmpty() }) {
                    pos
                } else null
            }
        }.filter { it.second.size == 1 }.forEach { (rule, possibles) ->
            solved[possibles.first()] = rule
        }
    }
    println(solved)
    println(solved.filter { it.value.first.startsWith("departure") }.map {
        ticket[it.key].toLong()
    }.reduce { acc, value -> acc * value })

}