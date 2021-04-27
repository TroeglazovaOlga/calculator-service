package io.primeaspect.calculator.repository.impl.mybatis.number

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.primeaspect.calculator.dto.response.NumberResponse
import io.primeaspect.calculator.model.Number
import io.primeaspect.calculator.repository.impl.mybatis.NumberStatisticsRepositoryMyBatis
import io.primeaspect.calculator.repository.impl.mybatis.mapper.NumberStatisticsMapper
import org.junit.jupiter.api.Test
import java.time.LocalDate

class NumberRepositoryMyBatisUnitTest {
    private val mapper: NumberStatisticsMapper = mock()
    private val repository = NumberStatisticsRepositoryMyBatis(mapper)

    @Test
    fun `The save(request) method of the crudRepository is called when the saveNumber(request) method of the repository is called`() {
        val request = Number(value = 1.0, date = LocalDate.now(), count = 10)
        repository.saveNumber(request)
        verify(mapper).saveNumber(request)
    }

    @Test
    fun `The getPopularNumber() method of the crudRepository is called when the getPopularNumber() method of the repository is called`() {
        val expected = NumberResponse(value = 1.0, count = 10)
        val expectedList: List<NumberResponse> = listOf(expected)

        whenever<List<NumberResponse>>(mapper.getPopularNumber()).thenReturn(expectedList)
        repository.getPopularNumber()

        verify(mapper).getPopularNumber()
    }

    @Test
    fun `The getPopularNumberByDate() method of the crudRepository is called when the getPopularNumberByDate() method of the repository is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val expected = NumberResponse(value = 1.0, count = 10)
        val expectedList: List<NumberResponse> = listOf(expected)

        whenever<List<NumberResponse>>(mapper.getPopularNumberByDate(date)).thenReturn(expectedList)
        repository.getPopularNumberByDate(date)

        verify(mapper).getPopularNumberByDate(date)
    }
}