package io.primeaspect.calculator.controller

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.primeaspect.calculator.dto.response.ExpressionListResponse
import io.primeaspect.calculator.dto.response.NumberResponse
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.service.StatisticsService
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

@WebMvcTest(controllers = [StatisticsController::class])
class StatisticsControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var service: StatisticsService

    @Test
    fun `Returns success status and a list of all expressions filtered by date when a GET request is sent`() {
        val date = LocalDate.of(2017, 12, 15)
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(response)
        val expected = ExpressionListResponse(responseList)

        whenever(service.findAllByDate(date)).thenReturn(expected)

        mvc.perform(MockMvcRequestBuilders.get("/statistics/date?date=2017-12-15"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.list").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$.list", hasSize<Any>(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].date", `is`(response.date.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].expression", `is`(response.expression)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].solution", `is`(response.solution)))
        verify(service).findAllByDate(response.date)
    }

    @Test
    fun `Returns success status and a list of all expressions filtered by operation when a GET request is sent`() {
        val date = LocalDate.of(2017, 12, 15)
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(response)
        val expected = ExpressionListResponse(responseList)

        whenever(service.findAllByOperation("^")).thenReturn(expected)

        mvc.perform(MockMvcRequestBuilders.get("/statistics/operation?operation=^"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.list").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$.list", hasSize<Any>(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].date", `is`(response.date.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].expression", `is`(response.expression)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].solution", `is`(response.solution)))
        verify(service).findAllByOperation("^")
    }

    @Test
    fun `Returns success status and count of all expressions filtered by date when a GET request is sent`() {
        val date = LocalDate.of(2017, 12, 15)

        whenever(service.getCountByDate(date)).thenReturn(5)

        mvc.perform(MockMvcRequestBuilders.get("/statistics/count/date?date=2017-12-15"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", `is`(5)))
        verify(service).getCountByDate(date)
    }

    @Test
    fun `Returns success status and count of all expressions filtered by operation when a GET request is sent`() {
        whenever(service.getCountByOperation("+")).thenReturn(5)

        mvc.perform(MockMvcRequestBuilders.get("/statistics/count/operation?operation=+"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", `is`(5)))
        verify(service).getCountByOperation("+")
    }

    @Test
    fun `Returns success status and the most popular number when a GET request is sent`() {
        val expected = NumberResponse(value = 10.0, count = 5)

        whenever(service.getPopularNumber()).thenReturn(expected)

        mvc.perform(MockMvcRequestBuilders.get("/statistics/popular"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", `is`(expected.value)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count", `is`(expected.count)))
        verify(service).getPopularNumber()
    }

    @Test
    fun `Returns success status and the most popular number by date when a GET request is sent`() {
        val date = LocalDate.of(2017, 12, 15)
        val expected = NumberResponse(value = 10.0, count = 5)

        whenever(service.getPopularNumberByDate(date)).thenReturn(expected)

        mvc.perform(MockMvcRequestBuilders.get("/statistics/popular/date?date=2017-12-15"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", `is`(expected.value)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count", `is`(expected.count)))
        verify(service).getPopularNumberByDate(date)
    }
}