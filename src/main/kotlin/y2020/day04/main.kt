// Today is ugly little functional version day

package y2020.day04

import java.io.File

val required = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
val validEyes = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

infix fun String?.inRange(v: Pair<Int, Int>): Boolean = this?.toIntOrNull() in v.first..v.second
fun String?.rFindGroups(r: String) = r.toRegex().find(this ?: "")?.groupValues
fun String?.rMatches(r: String) = (this?.matches(r.toRegex()) == true)

fun main() {
    val text = File("data/2020/day04/input.txt").readText().split("\n\n")
    text.map { it ->
        it.split(" ", "\n").filter { it.isNotBlank() }.map {
            it.split(":").let { el -> el[0] to el[1] }
        }.toMap()
    }.filter { it.keys.containsAll(required) }.also { println("Part1: ${it.size}") }.filter { pMap ->
        (pMap["byr"] inRange (1920 to 2002)) && (pMap["iyr"] inRange (2010 to 2020)) &&
                (pMap["eyr"] inRange (2020 to 2030)) &&
                (pMap["hgt"].rFindGroups("([0-9]+(?=in))|([0-9]+(?=cm))")?.let {
                    (it[1] inRange (59 to 76)) || (it[2] inRange (150 to 193))
                } == true) &&
                (pMap["hcl"].rMatches("#[0-9a-f]{6}")) && (pMap["ecl"] in validEyes) &&
                (pMap["pid"].rMatches("[0-9]{9}"))
    }.let { println("Part2: ${it.size}") }
}