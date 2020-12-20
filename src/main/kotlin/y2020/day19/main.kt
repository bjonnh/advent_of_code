package y2020.day19

import java.io.File
import kotlin.system.measureTimeMillis

fun Map<String, String>.toRegStr(rule: String, l: Int = 0): String {
    if (rule == "42 | 42 8") return "(${this.toRegStr(this["42"]!!, l)})+"
    if ((l > 0) && (rule == "42 31 | 42 11 31"))
        return "(${this.toRegStr(this["42"]!!, 0)}){$l}(${this.toRegStr(this["31"]!!, 0)}){$l}"
    return rule.split("|").map { partOfRule ->
        partOfRule.trim().split(" ").mapNotNull {
            if ((it.startsWith('\"')) && (it.endsWith('\"'))) {
                it.trim('\"')
            } else "(${toRegStr(this[it]!!, l)})"
        }.joinToString("")
    }.joinToString("|")
}

fun main() {
    val source = File("data/2020/19_input.txt").readText()
    val (rulesRaw, entriesRaw) = source.split("\n\n")
    val rules = rulesRaw.split("\n").map { it.split(":") }.map { it[0] to it[1] }.toMap().toMutableMap()
    val entries = entriesRaw.split("\n")
    rules.toRegStr(rules["0"]!!).toRegex().let { rule0 -> println(entries.count { rule0.matches(it) }) }
    rules["8"] = "42 | 42 8"
    rules["11"] = "42 31 | 42 11 31"
    /*val time = measureTimeMillis {
        println((1..(entries.maxOf { it.length / 16 })).map { it }.parallelStream().map { level ->
            rules.toRegStr(rules["0"]!!, level).toRegex().let { newRule0 -> entries.count { newRule0.matches(it) } }
        }.reduce(0, Integer::sum))
    }
    println("calculated in $time")*/

    // Optimized version that only checks strings that didn't match before
    var matching = 0
    val time2 = measureTimeMillis {
        val max = (entries.maxOf { it.length / 16 })
        var count = 1
        var entriesDel = entries.toMutableList()
        while (count < max) {
            val rule = rules.toRegStr(rules["0"]!!, count).toRegex()
            val nonMatching = entriesDel.filter { !rule.matches(it) }.toMutableList()
            matching += entriesDel.size - nonMatching.size
            entriesDel = nonMatching
            count++
        }
    }
    println("$matching in $time2")
}
