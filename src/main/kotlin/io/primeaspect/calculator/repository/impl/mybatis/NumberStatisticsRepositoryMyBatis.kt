package io.primeaspect.calculator.repository.impl.mybatis

import io.primeaspect.calculator.model.Number
import io.primeaspect.calculator.repository.NumberStatisticsRepository
import io.primeaspect.calculator.repository.impl.mybatis.mapper.NumberStatisticsMapper
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class NumberStatisticsRepositoryMyBatis(private val mapper: NumberStatisticsMapper) : NumberStatisticsRepository {
    override fun saveNumber(number: Number) {
        mapper.saveNumber(number)
    }

    override fun updateNumber(number: Number) {
        mapper.updateNumber(number)
    }

    override fun findNumber(value: Double, date: LocalDate) = mapper.findNumber(value, date)

    override fun getPopularNumber() = mapper.getPopularNumber()[0]

    override fun getPopularNumberByDate(date: LocalDate) = mapper.getPopularNumberByDate(date)[0]

    override fun deleteAllNumbers() {
        mapper.deleteAllNumbers()
    }
}