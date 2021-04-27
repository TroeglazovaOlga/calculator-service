package io.primeaspect.calculator.repository.impl.mybatis.mapper

import io.primeaspect.calculator.dto.response.NumberResponse
import io.primeaspect.calculator.model.Number
import org.apache.ibatis.annotations.*
import java.time.LocalDate

@Mapper
interface NumberStatisticsMapper {
    @Insert(
            "INSERT INTO number (value, date, count) VALUES " +
                    "(#{number.value}, #{number.date}, #{number.count})"
    )
    fun saveNumber(@Param("number") number: Number)

    @Update("UPDATE number SET count=#{number.count} WHERE value=#{number.value} and date=#{number.date}")
    fun updateNumber(@Param("number") number: Number)

    @Select("SELECT value, sum(count)" +
            " FROM number GROUP BY value ORDER BY sum(count) DESC")
    fun getPopularNumber(): MutableList<NumberResponse>

    @Select("SELECT value, count FROM number WHERE count=(SELECT MAX(count) FROM number WHERE date=#{date})")
    fun getPopularNumberByDate(date: LocalDate): MutableList<NumberResponse>

    @Select("SELECT * FROM number WHERE value=#{value} and date=#{date}")
    fun findNumber(value: Double, date: LocalDate): MutableList<Number>

    @Delete("DELETE FROM number")
    fun deleteAllNumbers()
}