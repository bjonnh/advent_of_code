package y2020.day21

import java.io.File

fun main() {
    val input = File("data/2020/21_input.txt").readLines().map {
        val (ingreRaw, allergRaw) = it.trim(')').split(" (contains ")
        Pair(ingreRaw.split(" "), allergRaw.split(", "))
    }

    val ingredients = input.flatMap { it.first }.toSet()
    val allergens = input.flatMap { it.second }.toSet()

    // We make a map of all the allergens to all the ingredients
    // And we are going to remove the ingredients little by little
    val allergensToIngredients = allergens.map { allergen -> allergen to ingredients.toSet().toMutableSet() }.toMap()

    val finishedIngredients = mutableSetOf<String>() // We don't need that really, but that made it easier

    // We loop until we have solved all the allergens/ingredients couples, this will also solve the soy case of the example
    while (finishedIngredients.size != allergens.size) {
        allergensToIngredients.filter { it.value.size > 1 }
            .map { (allergen, _) -> // Look for all the allergens still not solved
                allergen to input.filter { it.second.contains(allergen) } // Select the recipes that contain that allergen
                    .map { it.first.filterNot { it in finishedIngredients } } // Remove all the ingredients we already know from those recipes
                // Reduce the recipe to their common ingredients (Solve the dairy case of the example)
            }.map { it.first to it.second.reduce { acc, list -> acc.toSet().intersect(list.toSet()).toList() } }
            .filter { it.second.size == 1 }.forEach { (first, second) -> // If we have solved it already
                allergensToIngredients[first]?.retainAll(second) // make sure our allergen to ingredient map is updated
                // and that we remove all the possibilites from our map
                allergensToIngredients.filterNot { it.key == first }.forEach { it.value.remove(second.first()) }
            }
        // We add in the finished ingredients anything that has been resolved (this will solve the fish case of the example)
        finishedIngredients.addAll(allergensToIngredients.filter { it.value.size == 1 }.map { it.value.first() })
    }
    println(input.map { it.first.count { it !in finishedIngredients } }.sum()) // How many times the non allergens appear
    // Sort the map by its keys and join the value
    println(allergensToIngredients.toSortedMap().map { it.value.first() }.joinToString(","))
}
