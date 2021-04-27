package io.primeaspect.calculator.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.primeaspect.calculator.dto.request.ExpressionRequest
import io.primeaspect.calculator.dto.response.ExpressionListResponse
import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.service.ExpressionService
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

@WebMvcTest(controllers = [ExpressionController::class])
class ExpressionControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var service: ExpressionService
    private val objectMapper = ObjectMapper()

    @Test
    fun `Returns success status and an expression when a POST request is sent`() {
        val date = LocalDate.of(2017, 12, 15)
        val request = ExpressionRequest("(-78+9-(9/4.5))^2")
        val expected = Expression(date = date, expression = request.expression, solution = 5401.0)

        whenever(service.create(request)).thenReturn(expected)

        mvc.perform(
                MockMvcRequestBuilders.post("/expression")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", `is`(expected.date.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expression", `is`(expected.expression)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.solution", `is`(expected.solution)))
                .andDo(MockMvcResultHandlers.print())
        verify(service).create(request)
    }

    @Test
    fun `Returns success status and a list of all expressions when a GET request is sent`() {
        val date = LocalDate.now()
        val response = Expression(date = date, expression = "(-78+9-(9/4.5))^2", solution = 5401.0)
        val responseList: List<Expression> = listOf(response)
        val expected = ExpressionListResponse(responseList)

        whenever(service.findAll()).thenReturn(expected)

        mvc.perform(MockMvcRequestBuilders.get("/expression"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.list").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$.list", hasSize<Any>(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].date", `is`(response.date.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].expression", `is`(response.expression)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].solution", `is`(response.solution)))
        verify(service).findAll()
    }
}