package io.primeaspect.calculator.repository.impl.mybatis

import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.repository.ExpressionRepository
import io.primeaspect.calculator.repository.impl.mybatis.mapper.ExpressionMapper
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ExpressionRepositoryMyBatis(private val mapper: ExpressionMapper) : ExpressionRepository {
    override fun saveExpression(expression: Expression) {
        mapper.saveExpression(expression)
    }

    override fun findAll() = mapper.findAll()

    override fun findAllByDate(date: LocalDate) = mapper.findAllByDate(date)

    override fun findAllByOperation(operation: String) = mapper.findAllByOperation(operation)

    override fun getCountByDate(date: LocalDate) = mapper.getCountByDate(date)

    override fun getCountByOperation(operation: String) = mapper.getCountByOperation(operation)

    override fun deleteAllExpressions() {
        mapper.deleteAllExpressions()
    }
}
