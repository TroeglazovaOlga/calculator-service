package io.primeaspect.calculator.service;

import io.primeaspect.calculator.dto.response.ExpressionListResponse;
import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.repository.ExpressionRepository;
import io.primeaspect.calculator.repository.NumberStatisticsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatisticsServiceTest {
    private final ExpressionRepository expressionRepository = Mockito.mock(ExpressionRepository.class);
    private final NumberStatisticsRepository numberRepository = Mockito.mock(NumberStatisticsRepository.class);
    private final StatisticsService service = new StatisticsService(expressionRepository, numberRepository);

    @Test
    public void findAllByDateTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression expression = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> expectedList = new ArrayList<>();
        expectedList.add(expression);
        ExpressionListResponse expected = new ExpressionListResponse(expectedList);

        when(expressionRepository.findAllByDate(date)).thenReturn(expectedList);

        ExpressionListResponse response = service.findAllByDate(date.toString());

        verify(expressionRepository).findAllByDate(date);
        Assertions.assertEquals(expected, response);
    }

    @Test
    public void findAllByOperationTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        String request = "^";
        Expression expression = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> expectedList = new ArrayList<>();
        expectedList.add(expression);
        ExpressionListResponse expected = new ExpressionListResponse(expectedList);

        when(expressionRepository.findAllByOperation(request)).thenReturn(expectedList);

        ExpressionListResponse response = service.findAllByOperation(request);

        verify(expressionRepository).findAllByOperation(request);
        Assertions.assertEquals(expected, response);
    }

    @Test
    public void getCountByDateTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        when(expressionRepository.getCountByDate(date)).thenReturn(5);

        int response = service.getCountByDate(date.toString());

        verify(expressionRepository).getCountByDate(date);
        Assertions.assertEquals(5, response);
    }

    @Test
    public void getCountByOperationTest() {
        String request = "^";
        when(expressionRepository.getCountByOperation(request)).thenReturn(5);

        int response = service.getCountByOperation(request);

        verify(expressionRepository).getCountByOperation(request);
        Assertions.assertEquals(5, response);
    }

    @Test
    public void getPopularNumberTest() {
        Number expected = new Number(10, 5);

        when(numberRepository.getPopularNumber()).thenReturn(expected);

        Number response = service.getPopularNumber();

        verify(numberRepository).getPopularNumber();
        Assertions.assertEquals(expected, response);
    }
}