package y2020.day21

import java.io.File

fun main() {
    val lines = File("data/2020/21_input.txt").readLines()
    val input = lines.map {
        val (ingreRaw, allergRaw) = it.trim(')').split(" (contains ")
        Pair(ingreRaw.split(" "), allergRaw.split(", "))
    }
    println(input)
    val ingredients = input.flatMap { it.first }.toSet()
    val allergens = input.flatMap { it.second }.toSet()

    println(ingredients)
    println(allergens)

    val allergensToIngredients = allergens.map { allergen ->
        allergen to ingredients.toSet().toMutableSet()
    }.toMap()

    val finishedIngredients = mutableSetOf<String>()

    // First pass take all the allergens that have >1 recipe, and find the intersection
    while (finishedIngredients.size != allergens.size) {
        val matches = allergensToIngredients.filter { it.value.size > 1 }.map { (allergen, content) ->

            allergen to input.filter { it.second.contains(allergen) }
                .map { it.first.filterNot { it in finishedIngredients } }
        }.map {
            it.first to it.second.reduce { acc, list -> acc.toSet().intersect(list.toSet()).toList() }
        }
        matches.filter {
            it.second.size == 1
        }.forEach { matching ->
            allergensToIngredients[matching.first]?.retainAll(matching.second)
            allergensToIngredients.filter { it.key != matching.first }
                .forEach { it.value.remove(matching.second.first()) }
        }
        finishedIngredients.addAll(allergensToIngredients.filter { it.value.size == 1 }.map { it.value.first() })
    }
    println(input.map { it.first.count { it !in finishedIngredients } }.sum())

    println(allergensToIngredients.toSortedMap().map { it.value.first() }.joinToString(",") )
}