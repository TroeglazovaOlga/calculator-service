package io.primeaspect.calculator.service;

import io.primeaspect.calculator.dto.request.ExpressionRequest;
import io.primeaspect.calculator.dto.response.ExpressionListResponse;
import io.primeaspect.calculator.exception.BadRequestException;
import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.repository.ExpressionRepository;
import io.primeaspect.calculator.repository.NumberStatisticsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExpressionServiceTest {
    private final CalculatorService calculator = Mockito.mock(CalculatorService.class);
    private final StatisticsService statisticsService = Mockito.mock(StatisticsService.class);
    private final ExpressionRepository expressionRepository = Mockito.mock(ExpressionRepository.class);
    private final ExpressionService service = new ExpressionService(calculator, statisticsService, expressionRepository);

    @Test
    public void createTest() throws BadRequestException {
        ExpressionRequest request = new ExpressionRequest("(-78+9-(9/4.5))^2");
        when(calculator.calculate(request.getExpression())).thenReturn(5401.0);

        LocalDate date = LocalDate.now();
        Expression expected = new Expression(date, request.getExpression(), 5401.0);

        Expression response = service.create(request);

        verify(calculator).calculate(eq(request.getExpression()));
        Assertions.assertEquals(expected, response);
    }

    @Test
    public void findAllTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression expression = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> expectedList = new ArrayList<>();
        expectedList.add(expression);
        ExpressionListResponse expected = new ExpressionListResponse(expectedList);

        when(expressionRepository.findAll()).thenReturn(expectedList);

        ExpressionListResponse response = service.findAll();

        verify(expressionRepository).findAll();
        Assertions.assertEquals(expected, response);
    }
}