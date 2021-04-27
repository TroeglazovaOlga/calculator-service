package io.primeaspect.calculator.service

import io.primeaspect.calculator.exception.BadRequestException
import io.primeaspect.calculator.service.enums.Delimiter
import io.primeaspect.calculator.service.enums.Operation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import java.util.*

@Service
class CalculatorService {
    private val logger: Logger = LogManager.getLogger(CalculatorService::class.java)

    fun calculate(expression: String) = try {
        val processor = ExpressionProcessor(expression)
        val reversePolishNotation = processor.getReversePolishNotation()
        calculateByReversePolishNotation(reversePolishNotation)
    } catch (e: Exception) {
        logger.error("Invalid request: $expression")
        throw BadRequestException()
    }

    private fun calculateByReversePolishNotation(expression: MutableList<String>): Double {
        val stack = Stack<Double>()

        while (expression.isNotEmpty()) {
            val token: String = expression.removeAt(0)
            if (Operation.isOperation(token)) {
                val operation = Operation.getOperation(token)
                val first = stack.pop()
                val second = stack.pop()
                stack.add(operation.action(second, first))
            } else {
                stack.add(token.toDouble())
            }
        }

        return stack.pop()
    }

    fun getNumbers(expression: String): Map<Double, Int> = ExpressionProcessor(expression).getNumbers()
}

internal class ExpressionProcessor(private val expression: String) {
    fun getReversePolishNotation(): MutableList<String> {
        val stack = Stack<String>()
        val outputList: MutableList<String> = LinkedList()
        val processedExpression = preProcessExpression(expression)
        val tokenizer = StringTokenizer(
            processedExpression,
            Delimiter.getDelimiters() + Operation.getOperations(),
            true
        )

        while (tokenizer.hasMoreTokens()) {
            val token = tokenizer.nextToken()
            when {
                stack.isEmpty() && (Operation.isOperation(token) || Delimiter.isDelimiter(token)) -> stack.add(token)

                Operation.isOperation(token) -> {
                    val curOperation = Operation.getOperation(token)
                    val prevToken = stack.peek()
                    val prevTokenPriority =
                        if (Operation.isOperation(prevToken))
                            Operation.getOperation(prevToken).priority
                        else Delimiter.getDelimiter(prevToken).priority
                    if (curOperation.priority <= prevTokenPriority) {
                        outputList.add(stack.pop())
                    }
                    stack.add(token)
                }

                (Delimiter.isDelimiter(token)) -> {
                    val delimiter = Delimiter.getDelimiter(token)
                    if (delimiter == Delimiter.RIGHT) {
                        while (stack.peek() != Delimiter.LEFT.value) {
                            outputList.add(stack.pop())
                        }
                        stack.pop()
                    } else {
                        stack.add(token)
                    }
                }

                else -> outputList.add(token)
            }
        }

        while (!stack.isEmpty()) {
            outputList.add(stack.pop())
        }

        return outputList
    }

    fun getNumbers(): Map<Double, Int> {
        val tokenizer = StringTokenizer(
            expression,
            Delimiter.getDelimiters() + Operation.getOperations()
        )
        val numbers: MutableMap<Double, Int> = HashMap()

        while (tokenizer.hasMoreTokens()) {
            val token = tokenizer.nextToken().toDouble()
            val count = numbers.getOrDefault(token, 0) + 1
            numbers[token] = count
        }

        return numbers
    }

    private fun preProcessExpression(expression: String): String {
        var result = expression.replace("\\s".toRegex(), "")
        if (result.startsWith("-")) {
            result = "0$result"
        }
        return result.replace("(-", "(0-")
    }
}