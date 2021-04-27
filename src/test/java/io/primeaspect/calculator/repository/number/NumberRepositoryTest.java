package io.primeaspect.calculator.repository.number;

import io.primeaspect.calculator.TestConfiguration;
import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.repository.NumberStatisticsRepository;
import io.primeaspect.calculator.repository.mapper.NumberStatisticsMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {TestConfiguration.class, NumberStatisticsRepository.class, NumberStatisticsMapper.class})
@ComponentScan(basePackages = "io.primeaspect.calculator")
@MapperScan("io.primeaspect.calculator.repository.mapper")
public class NumberRepositoryTest {
    @Autowired
    private NumberStatisticsRepository repository;

    @AfterEach
    public void cleanup() {
        repository.deleteAllNumbers();
    }

    @Test
    public void getPopularNumberTest() {
        Number firstRequest = new Number(1, 10);
        Number secondRequest = new Number(3, 3);
        repository.saveNumber(firstRequest);
        repository.saveNumber(secondRequest);

        Number response = repository.getPopularNumber();
        Assertions.assertEquals(firstRequest, response);
    }

    @Test
    public void saveNumberTest() {
        Number request = new Number(1, 10);
        repository.saveNumber(request);

        List<Number> response = repository.findNumber(request.getValue());

        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0), request);
    }
}