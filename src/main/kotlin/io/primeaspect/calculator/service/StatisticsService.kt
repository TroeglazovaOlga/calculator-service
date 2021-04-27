package io.primeaspect.calculator.service

import io.primeaspect.calculator.dto.response.ExpressionListResponse
import io.primeaspect.calculator.dto.response.NumberResponse
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.model.Number
import io.primeaspect.calculator.repository.ExpressionRepository
import io.primeaspect.calculator.repository.NumberStatisticsRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class StatisticsService(
        private val expressionRepository: ExpressionRepository,
        private val numberRepository: NumberStatisticsRepository
) {
    private val logger: Logger = LogManager.getLogger(StatisticsService::class.java)

    fun findAllByDate(date: LocalDate): ExpressionListResponse {
        val response = expressionRepository.findAllByDate(date)

        logger.debug("Find all expressions by date: $date")
        return ExpressionListResponse(response)
    }

    fun findAllByOperation(operation: String): ExpressionListResponse {
        val response = expressionRepository.findAllByOperation(operation)

        logger.debug("Find all expressions by operation: $operation")
        return ExpressionListResponse(response)
    }

    fun getCountByDate(date: LocalDate): Int {
        logger.debug("Get count of all expressions by date: $date")
        return expressionRepository.getCountByDate(date)
    }

    fun getCountByOperation(operation: String): Int {
        logger.debug("Get count of all expressions by operation: $operation")
        return expressionRepository.getCountByOperation(operation)
    }

    fun getPopularNumber(): NumberResponse {
        logger.debug("Get popular number")
        return numberRepository.getPopularNumber()
    }

    fun getPopularNumberByDate(date: LocalDate): NumberResponse {
        logger.debug("Get popular number by date: $date")
        return numberRepository.getPopularNumberByDate(date)
    }

    @Transactional
    fun saveNumbers(numbers: List<Number>) {
        logger.debug("Save numbers: $numbers")
        numbers.forEach {
            val numberFromDataBase =
                    numberRepository.findNumber(it.value, it.date)
            if (numberFromDataBase.isEmpty()) {
                numberRepository.saveNumber(it)
            } else {
                it.count = it.count!! + numberFromDataBase[0].count!!
                numberRepository.updateNumber(it)
            }
        }
    }

    fun getStatistics(expression: Expression) = io.primeaspect.calculator.dto.response.StatisticsResponse(
            expression,
            findAllByDate(expression.date),
            getPopularNumberByDate(expression.date)
    )
}