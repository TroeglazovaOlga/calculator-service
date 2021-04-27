package io.primeaspect.calculator.repository.impl.mybatis.expression

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.repository.impl.mybatis.ExpressionRepositoryMyBatis
import io.primeaspect.calculator.repository.impl.mybatis.mapper.ExpressionMapper
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ExpressionRepositoryMyBatisUnitTest {
    private val mapper: ExpressionMapper = mock()
    private val repository = ExpressionRepositoryMyBatis(mapper)

    @Test
    fun `The saveExpression(request) method of the mapper is called when the saveExpression(request) method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val request = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)

        repository.saveExpression(request)

        verify(mapper).saveExpression(eq(request))
    }

    @Test
    fun `The findAll() method of the mapper is called when the findAll() method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: MutableList<Expression> = mutableListOf(response)

        whenever(mapper.findAll()).thenReturn(responseList)
        repository.findAll()

        verify(mapper).findAll()
    }

    @Test
    fun `The findAllByDate(date) method of the mapper is called when the findAllByDate(date) method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(response)

        whenever<List<Expression>>(mapper.findAllByDate(date)).thenReturn(responseList)
        repository.findAllByDate(date)

        verify(mapper).findAllByDate(date)
    }

    @Test
    fun `The findAllByOperation(operation) method of the mapper is called when the findAllByOperation(operation) method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val operation = "^"
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(response)

        whenever<List<Expression>>(mapper.findAllByOperation(operation)).thenReturn(responseList)
        repository.findAllByOperation(operation)

        verify(mapper).findAllByOperation(operation)
    }

    @Test
    fun `The getCountByDate(date) method of the mapper is called when the getCountByDate(date) method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)

        whenever(mapper.getCountByDate(date)).thenReturn(5)
        repository.getCountByDate(date)

        verify(mapper).getCountByDate(date)
    }

    @Test
    fun `The getCountByOperation(operation) method of the mapper is called when the getCountByOperation(operation) method of the repository is called`() {
        val operation = "^"

        whenever(mapper.getCountByOperation(operation)).thenReturn(5)
        repository.getCountByOperation(operation)

        verify(mapper).getCountByOperation(operation)
    }
}