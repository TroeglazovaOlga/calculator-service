package io.primeaspect.calculator.repository.impl.hibernate.crudrepository

import io.primeaspect.calculator.model.Expression
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ExpressionCrudRepository : CrudRepository<Expression, Long> {
    fun findAllByDate(date: LocalDate): MutableList<Expression>

    @Query("select e from Expression e where expression like %:operation%")
    fun findAllByOperation(@Param("operation") operation: String): MutableList<Expression>

    fun countByDate(date: LocalDate): Int

    @Query("select count(e) from Expression e where expression like %:operation%")
    fun countByOperation(operation: String): Int
}