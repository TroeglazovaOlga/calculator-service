package io.primeaspect.calculator.repository.expression;

import io.primeaspect.calculator.TestConfiguration;
import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.repository.ExpressionRepository;
import io.primeaspect.calculator.repository.mapper.ExpressionMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {TestConfiguration.class, ExpressionRepository.class, ExpressionMapper.class})
@ComponentScan(basePackages = "io.primeaspect.calculator")
@MapperScan("io.primeaspect.calculator.repository.mapper")
public class ExpressionRepositoryTest {
    @Autowired
    private ExpressionRepository repository;

    @AfterEach
    public void cleanup() {
        repository.deleteAllExpressions();
    }

    @Test
    public void saveTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression request = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        List<Expression> responseList = new ArrayList<>();
        responseList.add(request);

        repository.saveExpression(request);

        Assertions.assertEquals(repository.findAll(), responseList);
    }

    @Test
    public void findAllByDateTest() {
        LocalDate firstDate = LocalDate.of(2017, 12, 15);
        LocalDate secondDate = LocalDate.of(2017, 12, 16);
        Expression firstRequest = new Expression(firstDate, "(-78+9-(9/4.5))^2", 5401.0);
        Expression secondRequest = new Expression(secondDate, "(-78+9-(9/4.5))^2", 5401.0);
        repository.saveExpression(firstRequest);
        repository.saveExpression(secondRequest);

        Expression expected = new Expression(firstDate, "(-78+9-(9/4.5))^2", 5401.0);

        List<Expression> response = repository.findAllByDate(firstDate);
        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0), expected);
    }

    @Test
    public void findAllByOperationTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression firstRequest = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        Expression secondRequest = new Expression(date, "2+2*2", 6.0);
        repository.saveExpression(firstRequest);
        repository.saveExpression(secondRequest);

        Expression expected = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);

        List<Expression> response = repository.findAllByOperation("^");
        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0), expected);
    }

    @Test
    public void getCountByDateTest() {
        LocalDate firstDate = LocalDate.of(2017, 12, 15);
        LocalDate secondDate = LocalDate.of(2017, 12, 16);
        Expression firstRequest = new Expression(firstDate, "(-78+9-(9/4.5))^2", 5401.0);
        Expression secondRequest = new Expression(secondDate, "(-78+9-(9/4.5))^2", 5401.0);
        repository.saveExpression(firstRequest);
        repository.saveExpression(secondRequest);

        int response = repository.getCountByDate(firstDate);
        Assertions.assertEquals(response, 1);
    }

    @Test
    public void getCountByOperationTest() {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Expression firstRequest = new Expression(date, "(-78+9-(9/4.5))^2", 5401.0);
        Expression secondRequest = new Expression(date, "2+2*2", 6.0);
        repository.saveExpression(firstRequest);
        repository.saveExpression(secondRequest);

        int response = repository.getCountByOperation("^");
        Assertions.assertEquals(response, 1);
    }
}