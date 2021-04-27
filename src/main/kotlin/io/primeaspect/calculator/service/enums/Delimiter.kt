package io.primeaspect.calculator.service.enums

enum class Delimiter(val value: String, val priority: Int) {
    LEFT("(", 1),
    RIGHT(")", 5);

    companion object {
        private var operations: String? = null

        fun isDelimiter(token: String?) = getDelimiters().contains(token!!)

        fun getDelimiter(token: String) = when (token) {
            "(" -> LEFT
            else -> RIGHT
        }

        fun getDelimiters(): String {
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