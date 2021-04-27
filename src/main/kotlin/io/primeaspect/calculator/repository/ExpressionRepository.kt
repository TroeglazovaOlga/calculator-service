package io.primeaspect.calculator.repository

import io.primeaspect.calculator.model.Expression
import java.time.LocalDate

interface ExpressionRepository {
    fun saveExpression(expression: Expression)
    fun findAll(): MutableList<Expression>
    fun findAllByDate(date: LocalDate): MutableList<Expression>
    fun findAllByOperation(operation: String): List<Expression>
    fun getCountByDate(date: LocalDate): Int
    fun getCountByOperation(operation: String): Int
    fun deleteAllExpressions()
}