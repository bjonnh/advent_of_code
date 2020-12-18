package y2020.day18

import java.io.File

fun lastOperatorOutsideParenthesis(expression: String, operators: List<Char>): Int {
    var parenthesis = 0
    expression.reversed().forEachIndexed { posInReversed, value ->
        if ((value in operators) && (parenthesis == 0)) return expression.length - posInReversed
        else if (value == '(') parenthesis++
        else if (value == ')') parenthesis--
    }
    return 0
}

fun evaluate(operators: List<List<Char>>, input: String): Long {
    val expression = input.trim()

    operators.forEach { operator ->
        while (true) {
            val pos = lastOperatorOutsideParenthesis(expression, operator)
            if (pos > 0) {
                val part1 = expression.substring(0, pos - 1)
                val part2 = expression.substring(pos)

                when (expression[pos - 1]) {
                    '+' -> return evaluate(operators, part1) + evaluate(operators, part2)
                    '*' -> return evaluate(operators, part1) * evaluate(operators, part2)
                }
            } else break
        }
    }
    if (expression.startsWith('(') && expression.endsWith(')'))
        return evaluate(operators, expression.substring(1, expression.length - 1))

    return expression.toLong() // If nothing matched we have a number so we are done \o/
}

fun main() {
    val data = File("data/2020/18_input.txt").readLines()
    println(data.map { evaluate(listOf(listOf('+', '*')), it) }.sum())
    println(data.map { evaluate(listOf(listOf('*'), listOf('+')), it) }.sum())
}