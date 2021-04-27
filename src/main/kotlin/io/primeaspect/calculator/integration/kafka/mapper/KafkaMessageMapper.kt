package io.primeaspect.calculator.integration.kafka.mapper

import io.primeaspect.calculator.ObjectFactory
import io.primeaspect.calculator.dto.response.StatisticsResponse
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import org.springframework.stereotype.Service
import org.springframework.xml.transform.StringResult
import javax.xml.datatype.DatatypeFactory

@Service
class KafkaMessageMapper(
        var statisticsResponseMarshaller: Jaxb2Marshaller
) {
    fun convertStatisticsResponseToXML(statisticsResponse: StatisticsResponse): String {
        val response = ObjectFactory().createStatisticsResponse()

        val expressionStatisticsResponse = statisticsResponse.expression
        val expression = ObjectFactory().createExpression().apply {
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(expressionStatisticsResponse.date.toString())
            expression = expressionStatisticsResponse.expression
            solution = expressionStatisticsResponse.solution
        }

        val allExpressionsStatisticsResponse = statisticsResponse.allExpressionsByDate
        val allExpressionsByDate = ObjectFactory().createExpressionListResponse().apply {
            allExpressionsStatisticsResponse.list.forEach {
                val expr = ObjectFactory().createExpression()
                expr.date = DatatypeFactory.newInstance().newXMLGregorianCalendar(it.date.toString())
                expr.expression = it.expression
                expr.solution = it.solution
                this.list.add(expr)
            }
        }

        val popularNumberStatisticsResponse = statisticsResponse.popularNumber
        val popularNumber = ObjectFactory().createNumberResponse().apply {
            value = popularNumberStatisticsResponse.value
            count = popularNumberStatisticsResponse.count!!
        }

        response.expression = expression
        response.allExpressionsByDate = allExpressionsByDate
        response.popularNumber = popularNumber

        val result = StringResult()
        statisticsResponseMarshaller.marshal(response, result)

        return result.toString()
    }
}