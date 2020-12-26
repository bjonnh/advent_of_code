package y2020.day21

import java.io.File

fun main() {
    val input = File("data/2020/21_input.txt").readLines().map {
        val (ingreRaw, allergRaw) = it.trim(')').split(" (contains ")
        Pair(ingreRaw.split(" "), allergRaw.split(", "))
    }

    val ingredients = input.flatMap { it.first }.toSet()
    val allergens = input.flatMap { it.second }.toSet()

    val allergensToIngredients = allergens.map { allergen ->
        allergen to ingredients.toSet().toMutableSet()
    }.toMap()

    val finishedIngredients = mutableSetOf<String>()

    while (finishedIngredients.size != allergens.size) {
        allergensToIngredients.filter { it.value.size > 1 }.map { (allergen, _) ->
            allergen to input.filter { it.second.contains(allergen) }
                .map { it.first.filterNot { it in finishedIngredients } }
        }.map { it.first to it.second.reduce { acc, list -> acc.toSet().intersect(list.toSet()).toList() } }
            .filter { it.second.size == 1 }.forEach { (first, second) ->
                allergensToIngredients[first]?.retainAll(second)
                allergensToIngredients.filterNot { it.key == first }.forEach { it.value.remove(second.first()) }
            }
        finishedIngredients.addAll(allergensToIngredients.filter { it.value.size == 1 }.map { it.value.first() })
    }
    println(input.map { it.first.count { it !in finishedIngredients } }.sum())
    println(allergensToIngredients.toSortedMap().map { it.value.first() }.joinToString(","))
}