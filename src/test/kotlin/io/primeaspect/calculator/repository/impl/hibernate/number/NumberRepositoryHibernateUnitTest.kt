package io.primeaspect.calculator.repository.impl.hibernate.number

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.primeaspect.calculator.dto.response.NumberResponse
import io.primeaspect.calculator.model.Number
import io.primeaspect.calculator.repository.impl.hibernate.NumberStatisticsRepositoryHibernate
import io.primeaspect.calculator.repository.impl.hibernate.crudrepository.NumberStatisticsCrudRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate

class NumberRepositoryHibernateUnitTest {
    private val crudRepository: NumberStatisticsCrudRepository = mock()
    private val repository = NumberStatisticsRepositoryHibernate(crudRepository)

    @Test
    fun `The save(request) method of the crudRepository is called when the saveNumber(request) method of the repository is called`() {
        val request = Number(value = 1.0, date = LocalDate.now(), count = 10)
        repository.saveNumber(request)
        verify(crudRepository).save(eq(request))
    }

    @Test
    fun `The getPopularNumber() method of the crudRepository is called when the getPopularNumber() method of the repository is called`() {
        val expected = NumberResponse(value = 1.0, count = 10)
        val expectedList: List<NumberResponse> = listOf(expected)

        whenever<List<NumberResponse>>(crudRepository.getPopularNumber()).thenReturn(expectedList)
        repository.getPopularNumber()

        verify(crudRepository).getPopularNumber()
    }

    @Test
    fun `The getPopularNumberByDate() method of the crudRepository is called when the getPopularNumberByDate() method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val expected = NumberResponse(value = 1.0, count = 10)
        val expectedList: List<NumberResponse> = listOf(expected)

        whenever<List<NumberResponse>>(crudRepository.getPopularNumberByDate(date)).thenReturn(expectedList)
        repository.getPopularNumberByDate(date)

        verify(crudRepository).getPopularNumberByDate(date)
    }
}
