package org.almarsk

class ArithmeticManager {

    fun evaluate(expression: String): Double {
        val tokens = expression.replace(" ", "").toList()
        val values = mutableListOf<Double>()
        val operators = mutableListOf<Char>()

        var i = 0
        while (i < tokens.size) {
            when {
                tokens[i].isDigit() || tokens[i] == '.' -> {
                    val start = i
                    while (i < tokens.size && (tokens[i].isDigit() || tokens[i] == '.')) {
                        i++
                    }
                    values.add(expression.substring(start, i).toDouble())
                    continue
                }

                tokens[i] == '(' -> operators.add(tokens[i])
                tokens[i] == ')' -> {
                    while (operators.isNotEmpty() && operators.last() != '(') {
                        values.add(
                            applyOperator(
                                operators.removeLast(),
                                values.removeLast(),
                                values.removeLast()
                            )
                        )
                    }
                    operators.removeLast() // Remove '('
                }

                tokens[i] in listOf('+', '-', '*', '/') -> {
                    while (operators.isNotEmpty() && hasPrecedence(tokens[i], operators.last())) {
                        values.add(
                            applyOperator(
                                operators.removeLast(),
                                values.removeLast(),
                                values.removeLast()
                            )
                        )
                    }
                    operators.add(tokens[i])
                }
            }
            i++
        }

        while (operators.isNotEmpty()) {
            values.add(
                applyOperator(
                    operators.removeLast(),
                    values.removeLast(),
                    values.removeLast()
                )
            )
        }

        return values.last()
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        return when {
            op2 == '(' || op2 == ')' -> false
            (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-') -> false
            else -> true
        }
    }

    private fun applyOperator(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0.0) error("Division by zero")
                a / b
            }

            else -> error("Unknown operator $op")
        }
    }
}