package y2020.day09

import helpers.linesFile

fun main() {
    val lines = linesFile("data/2020/09_input.txt").map { it.toLong() }
    (25 until lines.size).map { index ->
        val setOfNumbersPrevious = lines.subList(index - 25, index).toSet().toList()
        val valid = (setOfNumbersPrevious.indices).any { i ->
            (i until setOfNumbersPrevious.size).any { j ->
                (setOfNumbersPrevious[i] + setOfNumbersPrevious[j]) == lines[index]
            }
        }
        if (!valid) {
            println("Part 1: ${lines[index]}")
            (lines.indices).map { i ->
                (2 until lines.size - i).forEach { j ->
                    val arr = lines.subList(i, i + j)
                    if (arr.sum() == lines[index]) {
                        println("Found the range $i $j part2 result is ${arr.minOrNull()!! + arr.maxOrNull()!!}")
                        return // We are done with the program at this stage
                    }
                    if (arr.sum() > lines[index]) return@forEach
                }
            }
        }
    }
}