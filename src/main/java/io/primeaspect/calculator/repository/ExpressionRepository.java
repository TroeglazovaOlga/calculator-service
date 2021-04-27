package io.primeaspect.calculator.repository;

import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.repository.mapper.ExpressionMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ExpressionRepository {
    private final ExpressionMapper mapper;

    public ExpressionRepository(ExpressionMapper mapper) {
        this.mapper = mapper;
    }

    public void saveExpression(Expression expression) {
        mapper.saveExpression(expression);
    }

    public List<Expression> findAll() {
        return mapper.findAll();
    }

    public List<Expression> findAllByDate(LocalDate date) {
        return mapper.findAllByDate(date);
    }

    public List<Expression> findAllByOperation(String operation) {
        return mapper.findAllByOperation(operation);
    }

    public int getCountByDate(LocalDate date) {
        return mapper.getCountByDate(date);
    }

    public int getCountByOperation(String operation) {
        return mapper.getCountByOperation(operation);
    }

    public void deleteAllExpressions() {
        mapper.deleteAllExpressions();
    }
}