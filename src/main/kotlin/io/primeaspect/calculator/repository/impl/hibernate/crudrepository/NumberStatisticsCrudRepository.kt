package io.primeaspect.calculator.repository.impl.hibernate.crudrepository

import io.primeaspect.calculator.dto.response.NumberResponse
import io.primeaspect.calculator.model.Number
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface NumberStatisticsCrudRepository : CrudRepository<Number, Long> {
    @Modifying
    @Query("update Number n set n.count=:count where n.value=:value and n.date=:date")
    fun updateNumber(value: Double, date: LocalDate, count: Int)

    @Query("select new io.primeaspect.calculator.dto.response.NumberResponse(n.value, cast(sum(n.count) as int)) " +
            "from Number n group by n.value order by sum(n.count) desc")
    fun getPopularNumber(): MutableList<NumberResponse>

    @Query("select new io.primeaspect.calculator.dto.response.NumberResponse(num.value, cast(num.count as int))" +
            " from Number num where num.count=(select max(n.count) from Number n where date=:date)")
    fun getPopularNumberByDate(date: LocalDate): MutableList<NumberResponse>

    fun findAllByValueAndDate(value: Double, date: LocalDate): MutableList<Number>
}