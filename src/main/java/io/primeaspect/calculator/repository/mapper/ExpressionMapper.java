package io.primeaspect.calculator.repository.mapper;

import io.primeaspect.calculator.model.Expression;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ExpressionMapper {
    @Insert("INSERT INTO expression (date, expression, solution) VALUES " +
            "(#{expression.date}, #{expression.expression}, #{expression.solution})")
    void saveExpression(@Param("expression") Expression expression);

    @Select("SELECT * FROM expression")
    List<Expression> findAll();

    @Select("SELECT * FROM expression WHERE date=#{date}")
    List<Expression> findAllByDate(LocalDate date);

    @Select("SELECT * FROM expression WHERE expression LIKE '%${operation}%'")
    List<Expression> findAllByOperation(String operation);

    @Select("SELECT COUNT(*) FROM expression WHERE date=#{date}")
    int getCountByDate(LocalDate date);

    @Select("SELECT COUNT(*) FROM expression WHERE expression LIKE '%${operation}%'")
    int getCountByOperation(String operation);

    @Delete("DELETE FROM expression")
    void deleteAllExpressions();
}