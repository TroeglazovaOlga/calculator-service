package io.primeaspect.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.primeaspect.calculator.dto.request.ExpressionRequest;
import io.primeaspect.calculator.dto.response.ExpressionListResponse;
import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.service.ExpressionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExpressionController.class)
public class ExpressionControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ExpressionService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createTest() throws Exception {
        LocalDate date = LocalDate.of(2017, 12, 15);
        ExpressionRequest request = new ExpressionRequest("(-78+9-(9/4.5))^2");
        Expression expected = new Expression(date, request.getExpression(), 5401.0);

        when(service.create(request)).thenReturn(expected);

        mvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is(expected.getDate().toString())))
                .andExpect(jsonPath("$.expression", is(expected.getExpression())))
                .andExpect(jsonPath("$.solution", is(expected.getSolution())));

        verify(service).create(eq(request));
    }

    @Test
    public void findAllTest() throws Exception {
        LocalDate date = LocalDate.now();
        Expression response = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> responseList = new ArrayList<>();
        responseList.add(response);
        ExpressionListResponse expected = new ExpressionListResponse(responseList);

        when(service.findAll()).thenReturn(expected);

        mvc.perform(get("/expression"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isArray())
                .andExpect(jsonPath("$.list", hasSize(1)))
                .andExpect(jsonPath("$.list[0].date", is(response.getDate().toString())))
                .andExpect(jsonPath("$.list[0].expression", is(response.getExpression())))
                .andExpect(jsonPath("$.list[0].solution", is(response.getSolution())));

        verify(service).findAll();
    }
}