package y2015.day12

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import helpers.textFile
import java.io.StringReader

fun parse(input: String) = Parser.default().parse(StringReader(input))

fun reader(input: Any, valueToSearch: String): MutableList<Int> {
    val numberCollection = mutableListOf<Int>()
    when (input) {
        is JsonObject -> {
            if (!input.map.any { it.value == valueToSearch }) {
                input.map.forEach { numberCollection.addAll(reader(it.value as Any, valueToSearch)) }
            }
        }
        is JsonArray<*> -> input.map { numberCollection.addAll(reader(it as Any, valueToSearch)) }
        is Int -> numberCollection.add(input)
    }
    return numberCollection
}

fun main() {
    val file = parse(textFile("data/2015/12_input.txt"))
    println(reader(file, "æ").sum()) // Initially I had a regexp for that but that doesn't work for part2…
    println(reader(file, "red").sum())
}