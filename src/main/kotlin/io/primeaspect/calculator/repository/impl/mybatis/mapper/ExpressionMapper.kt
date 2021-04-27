package io.primeaspect.calculator.repository.impl.mybatis.mapper

import io.primeaspect.calculator.model.Expression
import org.apache.ibatis.annotations.*
import java.time.LocalDate

@Mapper
interface ExpressionMapper {
    @Insert(
            "INSERT INTO expression (date, expression, solution) VALUES " +
                    "(#{expression.date}, #{expression.expression}, #{expression.solution})"
    )
    fun saveExpression(@Param("expression") expression: Expression?)

    @Select("SELECT * FROM expression")
    fun findAll(): MutableList<Expression>

    @Select("SELECT * FROM expression WHERE date=#{date}")
    fun findAllByDate(date: LocalDate?): MutableList<Expression>

    @Select("SELECT * FROM expression WHERE expression LIKE '%\${operation}%'")
    fun findAllByOperation(operation: String?): MutableList<Expression>

    @Select("SELECT COUNT(*) FROM expression WHERE date=#{date}")
    fun getCountByDate(date: LocalDate?): Int

    @Select("SELECT COUNT(*) FROM expression WHERE expression LIKE '%\${operation}%'")
    fun getCountByOperation(operation: String?): Int

    @Delete("DELETE FROM expression")
    fun deleteAllExpressions()
}