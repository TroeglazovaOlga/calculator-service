package io.primeaspect.calculator.service.enums

import kotlin.math.pow

enum class Operation(val value: String, val priority: Int) {
    SUM("+", 2) {
        override fun action(x: Double, y: Double) = x + y
    },
    SUBTRACT("-", 2) {
        override fun action(x: Double, y: Double) = x - y
    },
    MULTIPLY("*", 3) {
        override fun action(x: Double, y: Double) = x * y
    },
    DIVISION("/", 3) {
        override fun action(x: Double, y: Double) = x / y
    },
    POW("^", 4) {
        override fun action(x: Double, y: Double) = x.pow(y)
    };

    abstract fun action(x: Double, y: Double): Double

    companion object {
        private var operations: String? = null

        fun isOperation(token: String) = operations!!.contains(token)

        fun getOperation(token: String) = when (token) {
            "+" -> SUM
            "-" -> SUBTRACT
            "*" -> MULTIPLY
            "/" -> DIVISION
            else -> POW
        }

        fun getOperations(): String {
            if (operations == null) {
                val result = StringBuilder()

                values().forEach {
                    result.append(it.value)
                }

                operations = result.toString()
            }
            return operations!!
        }
    }
}