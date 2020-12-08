package y2015.day08

import helpers.linesFile

enum class Mode {
    NORMAL,
    ESCAPE,
    HEX
}

fun main() {
    val lines = linesFile("data/2015/08_input.txt")

    val values = lines.map {
        var mode = Mode.NORMAL
        var newChain = ""
        var hexPair = ""
        it.subSequence(1, it.length - 1).forEach {
            when (mode) {
                Mode.NORMAL -> {
                    if (it == '\\') mode = Mode.ESCAPE else newChain += it
                }
                Mode.ESCAPE -> {
                    when (it) {
                        '\\' -> {
                            mode = Mode.NORMAL
                            newChain += "\\"
                        }
                        '\"' -> {
                            mode = Mode.NORMAL
                            newChain += "\""
                        }
                        'x' -> {
                            mode = Mode.HEX
                        }
                        else -> throw RuntimeException("We do not handle that case in escape mode of string $it ")
                    }
                }
                Mode.HEX -> {
                    when (hexPair.length) {
                        0 -> {
                            hexPair += it
                        }
                        1 -> {
                            mode = Mode.NORMAL
                            newChain += Integer.parseInt(hexPair + it, 16).toChar()
                            hexPair = ""
                        }
                    }
                }
            }
        }

        Pair(it.length, newChain.length)
    }

    println(values.sumBy { it.first - it.second })

    val reencode = lines.map {
        val new = "\"" + it.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
        Pair(it.length, new.length)
    }
    println(reencode.sumBy { it.second - it.first })
}