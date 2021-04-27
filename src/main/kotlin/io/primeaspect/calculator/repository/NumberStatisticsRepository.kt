package io.primeaspect.calculator.repository

import io.primeaspect.calculator.dto.response.NumberResponse
import io.primeaspect.calculator.model.Number
import java.time.LocalDate

interface NumberStatisticsRepository {
    fun saveNumber(number: Number)
    fun updateNumber(number: Number)
    fun getPopularNumber(): NumberResponse
    fun getPopularNumberByDate(date: LocalDate): NumberResponse
    fun findNumber(value: Double, date: LocalDate): MutableList<Number>
    fun deleteAllNumbers()
}