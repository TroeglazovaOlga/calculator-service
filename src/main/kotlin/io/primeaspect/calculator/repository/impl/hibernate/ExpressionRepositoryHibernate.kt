package io.primeaspect.calculator.repository.impl.hibernate

import io.primeaspect.calculator.model.Expression
import io.primeaspect.calculator.repository.ExpressionRepository
import io.primeaspect.calculator.repository.impl.hibernate.crudrepository.ExpressionCrudRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Primary
@Repository
class ExpressionRepositoryHibernate(private val crudRepository: ExpressionCrudRepository) : ExpressionRepository {
    override fun saveExpression(expression: Expression) {
        crudRepository.save(expression)
    }

    override fun findAll() = crudRepository.findAll().toMutableList()

    override fun findAllByDate(date: LocalDate) = crudRepository.findAllByDate(date)

    override fun findAllByOperation(operation: String) = crudRepository.findAllByOperation(operation)

    override fun getCountByDate(date: LocalDate) = crudRepository.countByDate(date)

    override fun getCountByOperation(operation: String) = crudRepository.countByOperation(operation)

    override fun deleteAllExpressions() {
        crudRepository.deleteAll()
    }
}