package io.primeaspect.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.primeaspect.calculator.dto.response.ExpressionListResponse;
import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatisticsController.class)
public class StatisticsControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private StatisticsService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void findAllByDateTest() throws Exception {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression response = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> responseList = new ArrayList<>();
        responseList.add(response);
        ExpressionListResponse expected = new ExpressionListResponse(responseList);

        when(service.findAllByDate(date.toString())).thenReturn(expected);

        mvc.perform(get("/statistics/date?date=2017-12-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isArray())
                .andExpect(jsonPath("$.list", hasSize(1)))
                .andExpect(jsonPath("$.list[0].date", is(response.getDate().toString())))
                .andExpect(jsonPath("$.list[0].expression", is(response.getExpression())))
                .andExpect(jsonPath("$.list[0].solution", is(response.getSolution())));

        verify(service).findAllByDate(response.getDate().toString());
    }

    @Test
    public void findAllByOperationTest() throws Exception {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression response = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> responseList = new ArrayList<>();
        responseList.add(response);
        ExpressionListResponse expected = new ExpressionListResponse(responseList);

        when(service.findAllByOperation("^")).thenReturn(expected);

        mvc.perform(get("/statistics/operation?operation=^"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isArray())
                .andExpect(jsonPath("$.list", hasSize(1)))
                .andExpect(jsonPath("$.list[0].date", is(response.getDate().toString())))
                .andExpect(jsonPath("$.list[0].expression", is(response.getExpression())))
                .andExpect(jsonPath("$.list[0].solution", is(response.getSolution())));

        verify(service).findAllByOperation("^");
    }

    @Test
    public void getCountByDateTest() throws Exception {
        LocalDate date = LocalDate.of(2017, 12, 15);
        when(service.getCountByDate(date.toString())).thenReturn(5);

        mvc.perform(get("/statistics/count/date?date=2017-12-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));

        verify(service).getCountByDate(date.toString());
    }

    @Test
    public void getCountByOperationTest() throws Exception {
        when(service.getCountByOperation("+")).thenReturn(5);

        mvc.perform(get("/statistics/count/operation?operation=+"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));

        verify(service).getCountByOperation("+");
    }

    @Test
    public void getPopularNumberTest() throws Exception {
        Number expected = new Number(10, 5);

        when(service.getPopularNumber()).thenReturn(expected);

        mvc.perform(get("/statistics/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", is(expected.getValue())))
                .andExpect(jsonPath("$.count", is(expected.getCount())));

        verify(service).getPopularNumber();
    }
}