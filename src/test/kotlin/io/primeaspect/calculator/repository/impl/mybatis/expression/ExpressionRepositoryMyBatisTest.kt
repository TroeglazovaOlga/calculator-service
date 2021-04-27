package io.primeaspect.calculator.repository.impl.mybatis.expression

import io.primeaspect.calculator.TestConfiguration
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.repository.impl.mybatis.ExpressionRepositoryMyBatis
import io.primeaspect.calculator.repository.impl.mybatis.mapper.ExpressionMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest(classes = [
    TestConfiguration::class,
    ExpressionRepositoryMyBatis::class,
    ExpressionMapper::class
], properties = ["spring.liquibase.change-log=classpath:db/changelog.xml"])
@ComponentScan(basePackages = ["io.primeaspect.calculator"])
@MapperScan("io.primeaspect.calculator.repository.impl.mybatis.mapper")
class ExpressionRepositoryMyBatisTest {
    @Autowired
    private lateinit var repository: ExpressionRepositoryMyBatis

    @AfterEach
    fun cleanup() {
        repository.deleteAllExpressions()
    }

    @Test
    fun `When the save method is called, the expression is saved to the database`() {
        val date = LocalDate.of(2017, 12, 15)
        val request = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(request)

        repository.saveExpression(request)

        assertEquals(repository.findAll().size, responseList.size)
        assertEquals(repository.findAll()[0].date, responseList[0].date)
        assertEquals(repository.findAll()[0].expression, responseList[0].expression)
        assertEquals(repository.findAll()[0].solution, responseList[0].solution)
    }

    @Test
    fun `Returns the list of expressions filtered by date when the findAllByDate method is called`() {
        val firstDate = LocalDate.of(2017, 12, 15)
        val secondDate = LocalDate.of(2017, 12, 16)
        val firstRequest = Expression(date = firstDate, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val secondRequest = Expression(date = secondDate, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        repository.saveExpression(firstRequest)
        repository.saveExpression(secondRequest)

        val expected = Expression(date = firstDate, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val response: List<Expression> = repository.findAllByDate(firstDate)

        assertEquals(response.size, 1)
        assertEquals(response[0].date, expected.date)
        assertEquals(response[0].expression, expected.expression)
        assertEquals(response[0].solution, expected.solution)
    }

    @Test
    fun `Returns the list of expressions filtered by operation when the findAllByOperation method is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val firstRequest = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val secondRequest = Expression(date = date, expression = "2+2*2", solution = 6.0)
        repository.saveExpression(firstRequest)
        repository.saveExpression(secondRequest)

        val expected = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val response: List<Expression> = repository.findAllByOperation("^")

        assertEquals(response.size, 1)
        assertEquals(response[0].date, expected.date)
        assertEquals(response[0].expression, expected.expression)
        assertEquals(response[0].solution, expected.solution)
    }

    @Test
    fun `Returns count of expressions filtered by date when the findAllByDate method is called`() {
        val firstDate = LocalDate.of(2017, 12, 15)
        val secondDate = LocalDate.of(2017, 12, 16)
        val firstRequest = Expression(date = firstDate, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val secondRequest = Expression(date = secondDate, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        repository.saveExpression(firstRequest)
        repository.saveExpression(secondRequest)

        val response = repository.getCountByDate(firstDate)

        assertEquals(response, 1)
    }

    @Test
    fun `Returns count of expressions filtered by operation when the findAllByOperation method is called`() {
        val date = LocalDate.of(2017, 12, 15)
        val firstRequest = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val secondRequest = Expression(date = date, expression = "2+2*2", solution = 6.0)
        repository.saveExpression(firstRequest)
        repository.saveExpression(secondRequest)

        val response = repository.getCountByOperation("^")

        assertEquals(response, 1)
    }
}