package io.primeaspect.calculator.service

import io.primeaspect.calculator.exception.BadRequestException
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CalculatorServiceTest {
    var service = CalculatorService()

    @ParameterizedTest
    @CsvSource(
            "(-78+9-(9/4.5))^2,5041.0",
            "9*1+4.5,13.5",
            "9*(-1)+4.5,-4.5",
            "-1+(6+10-4)/(1+1*2),3",
            "-(-4+6+10)/(1+1*2),-4"
    )
    fun `Returns the correct result of the expression calculation`(expression: String, res: String) {
        val result = service.calculate(expression)
        assertEquals(result, res.toDouble())
    }

    @ParameterizedTest
    @CsvSource(
            "(-78++9-(9/4.5))^2", "9-*1+4.5",
            "*9*(-1)+4.5",
            ")-1+(6+10-4)/(1+1*2)",
            "-(-4+6+10)/(1+1*2)+"
    )
    fun `Expected BadRequestException`(expression: String) {
        assertFailsWith<BadRequestException> {
            service.calculate(expression)
        }
    }
}