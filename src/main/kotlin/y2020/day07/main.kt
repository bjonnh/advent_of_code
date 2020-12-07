package y2020.day07

import helpers.linesFile
import java.io.File

data class Bag(
    val name: String,
    val content: MutableMap<Bag, Int>
) {
    fun contains(name: String): Boolean = content.keys.any { it -> (it.name == name) || it.contains(name) }
    fun count(): Int = content.map { (k, v) -> v * (1 + k.count()) }.sum()

    fun containsCached(name: String, mem: MutableMap<String, Boolean> = mutableMapOf()): Boolean = mem.getOrPut(name) {
        content.keys.any { it -> (it.name == name) || it.contains(name) }
    }
}

fun getData(file: File = File(".")): Map<String, Bag> {
    val absoluteBags = mutableMapOf<String, Bag>()
    linesFile(file.absolutePath+"/data/2020/07_input.txt").forEach { // we do that for jmh
        val (bagName, rest) = it.replace("""\sbags?[\.,]?""".toRegex(), "").split(" contain ")
        absoluteBags.putIfAbsent(bagName, Bag(bagName, mutableMapOf()))
        rest.split(" ").chunked(3).filter { it[1] != "other" }.forEach {
            val name = "${it[1]} ${it[2]}"
            absoluteBags.putIfAbsent(name, Bag(name, mutableMapOf()))
            absoluteBags[bagName]!!.content[absoluteBags[name]!!] = it[0].toInt()
        }
    }
    return absoluteBags
}

fun main() {
    val absoluteBags = getData()
    println(absoluteBags.count { (k, v) -> v.contains("shiny gold") }) // 185
    println(absoluteBags["shiny gold"]?.count()) // 89084
}