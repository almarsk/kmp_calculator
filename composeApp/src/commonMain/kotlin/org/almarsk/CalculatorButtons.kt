package org.almarsk

val calculatorArgs = listOf(
    listOf(
        CalculatorOperation.Add('('),
        CalculatorOperation.Add(')'),
        CalculatorOperation.Erase,
        CalculatorOperation.Clear,
    ),
    listOf(
        CalculatorOperation.Add('1'),
        CalculatorOperation.Add('2'),
        CalculatorOperation.Add('3'),
        CalculatorOperation.Add('+'),
    ),
    listOf(
        CalculatorOperation.Add('4'),
        CalculatorOperation.Add('5'),
        CalculatorOperation.Add('6'),
        CalculatorOperation.Add('-'),
    ),
    listOf(
        CalculatorOperation.Add('7'),
        CalculatorOperation.Add('8'),
        CalculatorOperation.Add('9'),
        CalculatorOperation.Add('*'),
    ),
    listOf(
        CalculatorOperation.Add('0'),
        CalculatorOperation.Add('.'),
        CalculatorOperation.Calculate,
        CalculatorOperation.Add('/'),
    )
)

sealed interface CalculatorOperation {
    val displayName: String

    data class Add(val arg: Char) : CalculatorOperation {
        override val displayName: String = arg.toString()
    }

    data object Erase : CalculatorOperation {
        override val displayName: String = "<"
    }

    data object Clear : CalculatorOperation {
        override val displayName: String = "c"
    }

    data object Calculate : CalculatorOperation {
        override val displayName: String = "="
    }
}