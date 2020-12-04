package y2015.day05

import java.io.File

val forbidenStrings = listOf("ab", "cd", "pq", "xy")

fun String.notForbiden(): Boolean =
    forbidenStrings.none { this.contains(it) }

fun String.doubleChars(): Boolean =
    ('a'.rangeTo('z')).count { this.contains(it.toString() + it.toString()) } >= 1

fun String.threeVowels(): Boolean =
    this.count { it in listOf('a', 'e', 'i', 'o', 'u') } >= 3

fun String.isNice(): Boolean =
    this.threeVowels() && this.doubleChars() && this.notForbiden()


fun String.containTwoFullPairs(): Boolean {
    var i = 0
    while (i < this.length - 1) {
        if (this.substring(i + 2).indexOf((this[i].toString() + this[i + 1].toString())) > -1)
            return true
        i += 1
    }
    return false
}

fun String.letterRepeatInBetween(): Boolean {
    var i = 0
    while (i < this.length - 2) {
        if (this[i] == this[i + 2]) {
            return true
        }
        i += 1
    }
    return false
}

fun String.isNicePart2(): Boolean =
    this.containTwoFullPairs() && this.letterRepeatInBetween()


fun main() {
    val niceOrNotStrings = listOf(
        "ugknbfddgicrmopn",
        "aaa",
        "jchzalrnumimnmhp",
        "haegwjzuvuyypxyu",
        "dvszwmarrgswjxmb"
    )
    niceOrNotStrings.forEach {
        if (it.isNice()) {
            println("$it is nice")
        } else {
            println("$it is naughty")
        }
    }

    val count = File("data/2015/04_input.txt").readLines().count {
        it.isNice()
    }

    println("We have $count nice strings in part1.")

    val niceOrNotStrings2 = listOf(
        "qjhvhtzxzqqjkmpb",
        "xxyxx",
        "uurcxstgmygtbstg",
        "ieodomkazucvgmuy",
        "aaabcdefghih"
    )

    niceOrNotStrings2.forEach {
        if (it.isNicePart2()) {
            println("$it is nice2")
        } else {
            println("$it is naughty2")
        }
    }

    val count2 = File("data/2015/04_input.txt").readLines().count {
        it.isNicePart2()
    }

    println("We have $count2 nice strings in part2.")
}