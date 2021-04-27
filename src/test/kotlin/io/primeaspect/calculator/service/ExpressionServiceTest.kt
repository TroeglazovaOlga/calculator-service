package io.primeaspect.calculator.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.primeaspect.calculator.dto.request.ExpressionRequest
import io.primeaspect.calculator.dto.response.ExpressionListResponse
import io.primeaspect.calculator.integration.kafka.mapper.KafkaMessageMapper
import io.primeaspect.calculator.integration.kafka.TestKafkaProducer
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.repository.ExpressionRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class ExpressionServiceTest {
    private val calculator: CalculatorService = mock()
    private val statisticsService: StatisticsService = mock()
    private val expressionRepository: ExpressionRepository = mock()
    private val producer: TestKafkaProducer = mock()
    private val mapper: KafkaMessageMapper = mock()
    private val service = ExpressionService(calculator, statisticsService, expressionRepository, producer, mapper)

    @Test
    fun `The calculate(expression) method of the calculator is called when the create(request) method of the service is called`() {
        val request = ExpressionRequest("(-78+9-(9/4.5))^2")
        whenever(calculator.calculate(request.expression)).thenReturn(5401.0)

        val date = LocalDate.now()
        val expected = Expression(date = date, expression = request.expression, solution = 5401.0)
        val response = service.create(request)

        verify(calculator).calculate(request.expression)
        assertEquals(expected.date, response.date)
        assertEquals(expected.expression, response.expression)
        assertEquals(expected.solution, response.solution)
    }

    @Test
    fun `The findAll() method of the expressionRepository is called when the findAll() method of the service is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val expression = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val expectedList: List<Expression> = listOf(expression)
        val expected = ExpressionListResponse(expectedList)

        whenever<List<Expression>>(expressionRepository.findAll()).thenReturn(expectedList)

        val response = service.findAll()

        verify(expressionRepository).findAll()
        assertEquals(expected.list, response.list)
    }
}