package io.primeaspect.calculator.repository.expression;

import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.repository.ExpressionRepository;
import io.primeaspect.calculator.repository.mapper.ExpressionMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExpressionRepositoryUnitTest {
    private final ExpressionMapper mapper = Mockito.mock(ExpressionMapper.class);
    private final ExpressionRepository repository = new ExpressionRepository(mapper);

    @Test
    public void saveTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression request = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        repository.saveExpression(request);

        verify(mapper).saveExpression(eq(request));
    }

    @Test
    public void findAllTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression response = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> responseList = new ArrayList<>();
        responseList.add(response);

        when(mapper.findAll()).thenReturn(responseList);
        repository.findAll();

        verify(mapper).findAll();
    }

    @Test
    public void findAllByNameTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression response = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> responseList = new ArrayList<>();
        responseList.add(response);

        when(mapper.findAllByDate(date)).thenReturn(responseList);
        repository.findAllByDate(date);

        verify(mapper).findAllByDate(date);
    }

    @Test
    public void findAllByOperationTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        String operation = "^";
        Expression response = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> responseList = new ArrayList<>();
        responseList.add(response);

        when(mapper.findAllByOperation(operation)).thenReturn(responseList);
        repository.findAllByOperation(operation);

        verify(mapper).findAllByOperation(operation);
    }


    @Test
    public void getCountByDateTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        when(mapper.getCountByDate(date)).thenReturn(5);
        repository.getCountByDate(date);
        verify(mapper).getCountByDate(date);
    }

    @Test
    public void getCountByOperationTest() {
        String operation = "^";
        when(mapper.getCountByOperation(operation)).thenReturn(5);
        repository.getCountByOperation(operation);
        verify(mapper).getCountByOperation(operation);
    }
}
