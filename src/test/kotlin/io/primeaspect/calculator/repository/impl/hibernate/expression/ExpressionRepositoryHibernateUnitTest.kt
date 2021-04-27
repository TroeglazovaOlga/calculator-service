package io.primeaspect.calculator.repository.impl.hibernate.expression

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.repository.impl.hibernate.ExpressionRepositoryHibernate
import io.primeaspect.calculator.repository.impl.hibernate.crudrepository.ExpressionCrudRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ExpressionRepositoryHibernateUnitTest {
    private val crudRepository: ExpressionCrudRepository = mock()
    private val repository = ExpressionRepositoryHibernate(crudRepository)

    @Test
    fun `The save(request) method of the crudRepository is called when the saveExpression(request) method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val request = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)

        repository.saveExpression(request)

        verify(crudRepository).save(eq(request))
    }

    @Test
    fun `The findAll() method of the crudRepository is called when the findAll() method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(response)

        whenever(crudRepository.findAll()).thenReturn(responseList)
        repository.findAll()

        verify(crudRepository).findAll()
    }

    @Test
    fun `The findAllByDate(date) method of the crudRepository is called when the findAllByDate(date) method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(response)

        whenever<List<Expression>>(crudRepository.findAllByDate(date)).thenReturn(responseList)
        repository.findAllByDate(date)

        verify(crudRepository).findAllByDate(date)
    }

    @Test
    fun `The findAllByOperation(operation) method of the crudRepository is called when the findAllByOperation(operation) method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val operation = "^"
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(response)

        whenever<List<Expression>>(crudRepository.findAllByOperation(operation)).thenReturn(responseList)
        repository.findAllByOperation(operation)

        verify(crudRepository).findAllByOperation(operation)
    }

    @Test
    fun `The countByDate(date) method of the crudRepository is called when the countByDate(date) method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)

        whenever(crudRepository.countByDate(date)).thenReturn(5)
        repository.getCountByDate(date)

        verify(crudRepository).countByDate(date)
    }

    @Test
    fun `The countByOperation(operation) method of the crudRepository is called when the countByOperation(operation) method of the repository is called`() {
        val operation = "^"

        whenever(crudRepository.countByOperation(operation)).thenReturn(5)
        repository.getCountByOperation(operation)

        verify(crudRepository).countByOperation(operation)
    }
}
