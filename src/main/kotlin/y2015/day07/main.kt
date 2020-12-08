package y2015.day07

import helpers.linesFile
import helpers.rFindGroupsDestructured

class UnhandledOperation(msg: String) : Exception(msg)
class UnhandledDataType(msg: String) : Exception(msg)

class Machine {
    val definitions = mutableMapOf<String, Expression>()
    val valueCache = mutableMapOf<String, Int>()

    fun load(input: Map<String, Expression>) {
        input.forEach {
            definitions[it.key] = it.value
        }
        valueCache.clear()
    }

    fun resolve(wire: String): Int {
        return valueCache.getOrPut(wire) {
            when (val expression = definitions[wire]) {
                is Number -> expression.value
                is Composed -> execute(expression.value)
                else -> wire.toIntOrNull() ?: throw UnhandledDataType(wire)
            }
        }
    }

    fun execute(composed: String): Int {
        val split = composed.split(" ")
        return when (split.size) {
            1 -> resolve(split[0])
            2 -> {
                if (split[0] == "NOT") 65535 - resolve(split[1])
                else throw UnhandledOperation(composed)
            }
            3 -> {
                when (split[1]) {
                    "OR" -> resolve(split[0]).or(resolve(split[2]))
                    "AND" -> resolve(split[0]).and(resolve(split[2]))
                    "LSHIFT" -> resolve(split[0]).shl(resolve(split[2]))
                    "RSHIFT" -> resolve(split[0]).shr(resolve(split[2]))
                    else -> throw UnhandledOperation(composed)
                }
            }
            else -> throw UnhandledOperation(composed)
        }
    }
}

sealed class Expression

class Number(val value: Int) : Expression()
class Composed(val value: String) : Expression()

fun main() {
    val machine = Machine()
    val definitions = linesFile("data/2015/07_input.txt").map { line ->
        val (lhs, rhs) = line.rFindGroupsDestructured("""([a-zA-Z0-9\s?!\-]+)\s->\s([a-z]+)""")
        val out: Expression = lhs.toIntOrNull()?.let {
            Number(it)
        } ?: Composed(lhs)
        rhs to out
    }.toMap()
    machine.load(definitions)
    val valPart1 = machine.resolve("a")
    println(valPart1)
    machine.load(mapOf("b" to Number(valPart1)))
    println(machine.resolve("a"))
}