package y2020.day02

import java.io.File

fun main() {
    val file = File("data/2020/day02/input.txt").readLines()
    //val file = listOf("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc")
    var value: Int = 0
    val valids = file.map { line ->
        val (nums, letterC, pass) = line.split(" ")
        val (low, high) = nums.split("-").map { it.toInt() }
        val letter = letterC.trimEnd(':').toCharArray()[0]
        val count = pass.count { it == letter }
        if ((count >= low) && (count <= high)) {
            1
        } else {
            0
        }
    }.sum()
    println("Valids in 1: $valids")

    val valids2 = file.map {
        val (nums, letterC, pass) = it.split(" ")
        val (low, high) = nums.split("-").map { it.toInt() }
        val letter = letterC.trimEnd(':').toCharArray()[0]

        val inPos1 = pass[low - 1] == letter
        val inPos2 = pass[high - 1] == letter
        if (inPos1.xor(inPos2)) {
            1
        } else {
            0
        }
    }.sum()
    println("Valids in mode 2: $valids2")


}