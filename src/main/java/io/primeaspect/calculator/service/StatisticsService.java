package io.primeaspect.calculator.service;

import io.primeaspect.calculator.dto.response.ExpressionListResponse;
import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.repository.ExpressionRepository;
import io.primeaspect.calculator.repository.NumberStatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatisticsService {
    private final ExpressionRepository expressionRepository;
    private final NumberStatisticsRepository numberRepository;

    public StatisticsService(ExpressionRepository expressionRepository,
                             NumberStatisticsRepository numberRepository) {
        this.expressionRepository = expressionRepository;
        this.numberRepository = numberRepository;
    }

    public ExpressionListResponse findAllByDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        List<Expression> response = expressionRepository.findAllByDate(localDate);
        return new ExpressionListResponse(response);
    }

    public ExpressionListResponse findAllByOperation(String operation) {
        List<Expression> response = expressionRepository.findAllByOperation(operation);
        return new ExpressionListResponse(response);
    }

    public int getCountByDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return expressionRepository.getCountByDate(localDate);
    }

    public int getCountByOperation(String operation) {
        return expressionRepository.getCountByOperation(operation);
    }

    public Number getPopularNumber() {
        return numberRepository.getPopularNumber();
    }

    @Transactional
    public void saveNumbers(List<Number> numbers) {
        numbers.forEach(number -> {
            List<Number> numberFromDataBase = numberRepository.findNumber(number.getValue());
            if (numberFromDataBase.isEmpty()) {
                numberRepository.saveNumber(number);
            } else {
                number.setCount(number.getCount() + numberFromDataBase.get(0).getCount());
                numberRepository.updateNumber(number);
            }
        });
    }
}