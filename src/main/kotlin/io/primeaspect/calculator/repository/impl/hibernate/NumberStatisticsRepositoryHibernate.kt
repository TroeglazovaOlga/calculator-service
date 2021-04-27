package io.primeaspect.calculator.repository.impl.hibernate

import io.primeaspect.calculator.model.Number
import io.primeaspect.calculator.repository.NumberStatisticsRepository
import io.primeaspect.calculator.repository.impl.hibernate.crudrepository.NumberStatisticsCrudRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Primary
@Repository
class NumberStatisticsRepositoryHibernate(private val crudRepository: NumberStatisticsCrudRepository) :
        NumberStatisticsRepository {
    override fun saveNumber(number: Number) {
        crudRepository.save(number)
    }

    override fun updateNumber(number: Number) {
        crudRepository.updateNumber(number.value, number.date, number.count!!)
    }

    override fun findNumber(value: Double, date: LocalDate) = crudRepository.findAllByValueAndDate(value, date)

    override fun getPopularNumber() = crudRepository.getPopularNumber()[0]

    override fun getPopularNumberByDate(date: LocalDate) = crudRepository.getPopularNumberByDate(date)[0]

    override fun deleteAllNumbers() {
        crudRepository.deleteAll()
    }
}