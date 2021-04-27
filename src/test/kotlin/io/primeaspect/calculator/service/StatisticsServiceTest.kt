package io.primeaspect.calculator.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.primeaspect.calculator.dto.response.ExpressionListResponse
import io.primeaspect.calculator.dto.response.NumberResponse
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.repository.ExpressionRepository
import io.primeaspect.calculator.repository.impl.mybatis.NumberStatisticsRepositoryMyBatis
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class StatisticsServiceTest {
    private val expressionRepository: ExpressionRepository = mock()
    private val numberRepository: NumberStatisticsRepositoryMyBatis = mock()
    private val service = StatisticsService(expressionRepository, numberRepository)

    @Test
    fun `The findAllByDate(date) method of the expressionRepository is called when the findAllByDate(date) method of the service is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val expression = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val expectedList: List<Expression> = listOf(expression)
        val (expected) = ExpressionListResponse(expectedList)

        whenever<List<Expression>>(expressionRepository.findAllByDate(date)).thenReturn(expectedList)

        val (response) = service.findAllByDate(date)

        verify(expressionRepository).findAllByDate(date)
        assertEquals(expected, response)
    }

    @Test
    fun `The findAllByOperation(request) method of the expressionRepository is called when the findAllByOperation(request) method of the service is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val request = "^"
        val expression = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val expectedList: List<Expression> = listOf(expression)
        val (expected) = ExpressionListResponse(expectedList)

        whenever(expressionRepository.findAllByOperation(request)).thenReturn(expectedList)

        val (response) = service.findAllByOperation(request)

        verify(expressionRepository).findAllByOperation(request)
        assertEquals(expected, response)
    }

    @Test
    fun `The getCountByDate(date) method of the expressionRepository is called when the getCountByDate(date) method of the service is called`() {
        val date = LocalDate.of(2017, 12, 15)
        whenever(expressionRepository.getCountByDate(date)).thenReturn(5)

        val response = service.getCountByDate(date)

        verify(expressionRepository).getCountByDate(date)
        assertEquals(5, response)
    }

    @Test
    fun `The getCountByOperation(request) method of the expressionRepository is called when the getCountByOperation(request) method of the service is called`() {
        val request = "^"
        whenever(expressionRepository.getCountByOperation(request)).thenReturn(5)

        val response = service.getCountByOperation(request)

        verify(expressionRepository).getCountByOperation(request)
        assertEquals(5, response)
    }

    @Test
    fun `The getPopularNumber() method of the numberRepository is called when the getPopularNumber() method of the service is called`() {
        val expected = NumberResponse(value = 10.0, count = 5)

        whenever(numberRepository.getPopularNumber()).thenReturn(expected)

        val response = service.getPopularNumber()

        verify(numberRepository).getPopularNumber()
        assertEquals(expected.value, response.value)
        assertEquals(expected.count, response.count)
    }

    @Test
    fun `The getPopularNumberByDate() method of the numberRepository is called when the getPopularNumberByDate() method of the service is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val expected = NumberResponse(value = 10.0, count = 5)

        whenever(numberRepository.getPopularNumberByDate(date)).thenReturn(expected)

        val response = service.getPopularNumberByDate(date)

        verify(numberRepository).getPopularNumberByDate(date)
        assertEquals(expected.value, response.value)
        assertEquals(expected.count, response.count)
    }
}