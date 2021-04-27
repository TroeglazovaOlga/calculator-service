package io.primeaspect.calculator.service;

import io.primeaspect.calculator.dto.request.ExpressionRequest;
import io.primeaspect.calculator.dto.response.ExpressionListResponse;
import io.primeaspect.calculator.exception.BadRequestException;
import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.repository.ExpressionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpressionService {
    private final CalculatorService calculator;
    private final StatisticsService statisticsService;
    private final ExpressionRepository expressionRepository;

    public ExpressionService(CalculatorService calculator,
                             StatisticsService statisticsService,
                             ExpressionRepository expressionRepository) {
        this.calculator = calculator;
        this.statisticsService = statisticsService;
        this.expressionRepository = expressionRepository;
    }

    @Transactional
    public Expression create(ExpressionRequest request) throws BadRequestException {
        LocalDate date = LocalDate.now();
        Double solution = calculator.calculate(request.getExpression());

        Expression expression = new Expression(date, request.getExpression(), solution);
        expressionRepository.saveExpression(expression);

        List<Number> numbers = calculator.getNumbers(request.getExpression());
        statisticsService.saveNumbers(numbers);
        return expression;
    }

    public ExpressionListResponse findAll() {
        List<Expression> response = expressionRepository.findAll();
        return new ExpressionListResponse(response);
    }
}