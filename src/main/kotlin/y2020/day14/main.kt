package y2020.day14

import helpers.linesFile
import kotlin.math.pow

fun main() {
    val currentMask = List(36) { '0' }.toMutableList()
    val memory = mutableMapOf<Int, Long>()
    val memory2 = mutableMapOf<Long, Long>()
    linesFile("data/2020/14_input.txt").map {
        val (command, value) = it.split(" = ")
        when (command) {
            "mask" -> value.mapIndexed { idx, it -> currentMask[idx] = it }
            else -> {
                val valueLong = value.toLong()
                val address = command.substring(4).trimEnd(']').toInt()

                val valPart1 = valueLong.toString(2).padStart(36, '0').mapIndexed { idx, it ->
                    when (val msk = currentMask[idx]) {
                        'X' -> it
                        else -> msk
                    }
                }.joinToString("").toLong(2)

                memory[address] = valPart1

                val numberofX = currentMask.count { it == 'X' }
                val addressPart2 = address.toString(2).padStart(36, '0')

                (0 until 2.0.pow(numberofX.toDouble()).toInt()).map { // Generate all the values that will replace the X
                    it.toString(2).padStart(numberofX, '0')
                }.forEach { newVals ->  // And we then replace the X with these
                    var count = 0
                    val addr = currentMask.mapIndexed { idx, it ->
                        when (it) {
                            '0' -> addressPart2[idx]
                            '1' -> '1'
                            else -> newVals[count].also { count++ }
                        }
                    }.joinToString("").toLong(2)
                    memory2[addr] = valueLong
                }
            }
        }
    }
    println(memory.values.sum())
    println(memory2.values.sum())
}