package y2020.day14

import helpers.linesFile
import java.lang.Math.pow


fun main() {
    val currentMask = List(36) { '0' }.toMutableList()
    val memory = mutableMapOf<Int, Long>()
    linesFile("data/2020/14_input.txt").map {
        val (command, value) = it.split(" = ")
        when (command) {
            "mask" -> value.mapIndexed { idx, it -> currentMask[idx] = it }
            else -> {
                val valueLong = value.toLong().toString(2).padStart(36, '0')
                val newVal = valueLong.mapIndexed { idx, it ->
                    when (currentMask[idx]) {
                        'X' -> it
                        '1' -> '1'
                        '0' -> '0'
                        else -> throw RuntimeException("Unknown symbol in $currentMask")
                    }
                }.joinToString("").toLong(2)
                val address = command.substring(4).trimEnd(']').toInt()
                memory[address] = newVal
            }
        }
    }
    println(memory.values.sum())

    val memory2 = mutableMapOf<Long, Long>()
    linesFile("data/2020/14_input.txt").map {
        val (command, value) = it.split(" = ")
        when (command) {
            "mask" -> value.mapIndexed { idx, it -> currentMask[idx] = it }
            else -> {
                val valueLong = value.toLong()
                val numberofX = currentMask.count { it == 'X' }
                val vals = (0 until pow(2.0, numberofX.toDouble()).toInt()).map {
                    it.toString(2).padStart(numberofX, '0')
                }
                val address = command.substring(4).trimEnd(']').toInt().toString(2).padStart(36, '0')
                vals.forEach { newVals ->
                    var count = 0
                    val addr = currentMask.mapIndexed { idx, it ->
                        when (it) {
                            '0' -> address[idx]
                            '1' -> '1'
                            'X' -> {
                                newVals[count].also { count++ }
                            }
                            else -> throw RuntimeException("Darn")
                        }
                    }.joinToString("").toLong(2)
                    memory2[addr] = valueLong
                }
            }
        }
    }
    println(memory2.values.sum())
}