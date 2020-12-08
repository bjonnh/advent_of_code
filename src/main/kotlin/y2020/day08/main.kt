package y2020.day08

import helpers.linesFile

sealed class Instruction

class NOP(val value: Int) : Instruction()
class ACC(val value: Int) : Instruction()
class JMP(val value: Int) : Instruction()

enum class State {
    RUNNING_STOP_ON_LOOP,
    STOPPED,
    FINISHED
}

class Machine {
    var state = State.STOPPED
    val seenInstruction = mutableSetOf<Int>()
    val jumpsOrNopsSeen = mutableSetOf<Pair<Int, Instruction>>()
    val instructions = mutableListOf<Instruction>()
    var acc = 0
    var ip = 0  // Instruction pointer
    fun runInstruction(pos: Int) {
        if ((state == State.RUNNING_STOP_ON_LOOP) && seenInstruction.contains(pos)) {
            state = State.STOPPED
            return
        }
        if (pos >= instructions.size-1) {
            state = State.FINISHED
            return
        }
        seenInstruction.add(pos)
        when (val instruction = instructions[pos]) {
            is ACC -> {
                acc += instruction.value
                ip += 1
            }
            is JMP -> {
                ip += instruction.value
                jumpsOrNopsSeen.add(Pair(pos, instruction))
            }
            else -> {
                jumpsOrNopsSeen.add(Pair(pos, instruction))
                ip += 1
            }
        }
    }

    fun run() {
        state = State.RUNNING_STOP_ON_LOOP
        while (state == State.RUNNING_STOP_ON_LOOP) {
            runInstruction(ip)
        }
    }

    fun reset() {
        ip = 0
        acc = 0
        seenInstruction.clear()
        jumpsOrNopsSeen.clear()
        instructions.clear()
    }

    fun load(newInstructions: List<Instruction>) =
        instructions.addAll(newInstructions)

    fun patch(pos: Int, instruction: Instruction) {
        instructions[pos] = instruction
    }
}

fun main() {
    val machine = Machine()
    val input = linesFile("data/2020/08_input.txt")
    val program = input.map {
        val (inst, value) = it.split(" ")
        when (inst) {
            "nop" -> NOP(value.toInt())
            "acc" -> ACC(value.toInt())
            "jmp" -> JMP(value.toInt())
            else -> throw RuntimeException("Unknown instruction $inst")
        }
    }
    machine.load(program)
    machine.run()
    println("Part1 ${machine.acc}")
    // part2
    val ops = machine.jumpsOrNopsSeen.toList()
    ops.forEach { // This is a dynamic patcher
        machine.reset()
        machine.load(program)
        when (val inst = it.second) {
            is NOP -> machine.patch(it.first, JMP(inst.value))
            is JMP -> machine.patch(it.first, NOP(inst.value))
        }
        machine.run()
        if (machine.state == State.FINISHED) {
            println("Part2 ${machine.acc}")
            return@forEach
        }
    }
}