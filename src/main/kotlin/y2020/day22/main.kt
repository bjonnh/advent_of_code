package y2020.day22

import java.io.File
import java.util.*

fun game(d1: List<Int>, d2: List<Int>, part2: Boolean): Pair<List<Int>, List<Int>> {
    val d1mut = d1.toMutableList()
    val d2mut = d2.toMutableList()
    val history = mutableSetOf(Objects.hash(d1mut, d2mut))
    while (d1mut.isNotEmpty() && d2mut.isNotEmpty()) {
        val card1 = d1mut.removeAt(0)
        val card2 = d2mut.removeAt(0)
        if (Objects.hash(d1mut, d2mut) in history) {
            return Pair(d1mut + listOf(card1, card2), listOf())
        } else {
            history.add(Objects.hash(d1mut, d2mut))
            val p1won = if (part2 && (d1mut.size >= card1) && (d2mut.size >= card2)) {
                game(d1mut.take(card1), d2mut.take(card2), part2).second.isEmpty()
            } else (card1 > card2)
            if (p1won) d1mut.addAll(listOf(card1, card2)) else d2mut.addAll(listOf(card2, card1))
        }
    }
    return Pair(d1mut, d2mut)
}

fun Pair<List<Int>, List<Int>>.score(): Int? =
    this.toList().maxByOrNull { it.size }?.reversed()?.mapIndexed { c, it -> (c + 1) * it }?.sum()

fun main() {
    val (d1, d2) = File("data/2020/22_input.txt").readText().split("\n\n")
        .map { it.split("\n").drop(1).map { it.toInt() } } // Game doesn't care who wins, but I'm going for the crab
    println(Pair(game(d1, d2, false).score(), game(d1, d2, true).score()))
}