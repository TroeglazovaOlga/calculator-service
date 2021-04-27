package io.primeaspect.calculator.service

import io.primeaspect.calculator.dto.request.ExpressionRequest
import io.primeaspect.calculator.dto.response.ExpressionListResponse
import io.primeaspect.calculator.integration.kafka.TestKafkaProducer
import io.primeaspect.calculator.integration.kafka.mapper.KafkaMessageMapper
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.model.Number
import io.primeaspect.calculator.repository.ExpressionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
class ExpressionService(
        private val calculator: CalculatorService,
        private val statisticsService: StatisticsService,
        private val expressionRepository: ExpressionRepository,
        private val producer: TestKafkaProducer,
        private val mapper: KafkaMessageMapper
) {
    private val logger: Logger = LogManager.getLogger(ExpressionService::class.java)

    @Transactional
    fun create(request: ExpressionRequest): Expression {
        val date = LocalDate.now()
        val solution = calculator.calculate(request.expression)
        val expression = Expression(date = date, expression = request.expression, solution = solution)
        expressionRepository.saveExpression(expression)

        val numbersMap = calculator.getNumbers(request.expression)
        val numbersList = mutableListOf<Number>()
        numbersMap.forEach { (key: Double?, value: Int?) ->
            numbersList.add(
                    Number(value = key, date = date, count = value)
            )
        }
        statisticsService.saveNumbers(numbersList)

        val statistics = statisticsService.getStatistics(expression)
        val statisticsXML = mapper.convertStatisticsResponseToXML(statistics)
        producer.sendMessage(statisticsXML)

        logger.debug("Create expression " + request.expression)
        return expression
    }

    fun findAll(): ExpressionListResponse {
        val response = expressionRepository.findAll()

        logger.debug("Find all expressions")
        return ExpressionListResponse(response)
    }
}