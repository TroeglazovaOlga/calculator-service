package io.primeaspect.calculator.repository.impl.hibernate.number

import io.primeaspect.calculator.TestConfiguration
import io.primeaspect.calculator.model.Number
import io.primeaspect.calculator.repository.impl.hibernate.NumberStatisticsRepositoryHibernate
import io.primeaspect.calculator.repository.impl.hibernate.crudrepository.NumberStatisticsCrudRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest(classes = [
    TestConfiguration::class,
    NumberStatisticsRepositoryHibernate::class
], properties = ["spring.liquibase.change-log=classpath:db/changelog.xml"])
@ComponentScan(basePackages = ["io.primeaspect.calculator"])
@EnableJpaRepositories(basePackageClasses = [NumberStatisticsCrudRepository::class])
class NumberRepositoryHibernateTest {
    @Autowired
    private lateinit var repository: NumberStatisticsRepositoryHibernate

    @AfterEach
    fun cleanup() {
        repository.deleteAllNumbers()
    }

    @Test
    fun `When the saveNumber method is called, the Number is saved to the database`() {
        val request = Number(value = 1.0, date = LocalDate.now(), count = 10)
        repository.saveNumber(request)

        val response: List<Number> = repository.findNumber(request.value, request.date)

        assertEquals(response.size, 1)
        assertEquals(response[0].value, request.value)
        assertEquals(response[0].date, request.date)
        assertEquals(response[0].count, request.count)
    }

    @Test
    fun `When getPopularNumber() is called, a Number(value = 1, count = 10) is returned`() {
        val firstDate = LocalDate.of(2017, 12, 15)
        val secondDate = LocalDate.of(2018, 12, 15)
        val firstRequest = Number(value = 1.0, date = firstDate, count = 2)
        val secondRequest = Number(value = 3.0, date = secondDate, count = 3)
        val thirdRequest = Number(value = 1.0, date = secondDate, count = 4)
        repository.saveNumber(firstRequest)
        repository.saveNumber(secondRequest)
        repository.saveNumber(thirdRequest)

        val response = repository.getPopularNumber()

        assertEquals(response.value, 1.0)
        assertEquals(response.count, 6)
    }

    @Test
    fun `When getPopularNumberByDate() is called, a Number(value = 1, date = firstDate, count = 2) is returned`() {
        val firstDate = LocalDate.of(2017, 12, 15)
        val secondDate = LocalDate.of(2018, 12, 15)
        val firstRequest = Number(value = 1.0, date = firstDate, count = 2)
        val secondRequest = Number(value = 3.0, date = secondDate, count = 3)
        repository.saveNumber(firstRequest)
        repository.saveNumber(secondRequest)

        val response = repository.getPopularNumberByDate(firstDate)

        assertEquals(firstRequest.value, response.value)
        assertEquals(firstRequest.count, response.count)
    }
}