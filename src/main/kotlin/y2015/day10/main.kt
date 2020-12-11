package y2015.day10


fun processString(input: String): String {
    val sb = StringBuilder()
    var old = ' '
    var count = 0
    input.map {
        if (it != old && old != ' ') {
            sb.append(count)
            sb.append(old)
            count = 0
        }
        count++
        old = it
    }
    sb.append(count)
    sb.append(old)
    return sb.toString()
}

fun main() {
    val input = "1321131112"
    var output = input
    repeat(40) {
        output = processString(output)
    }
    println(output.length)
    repeat(10) {
        output = processString(output)
    }
    println(output.length)
}