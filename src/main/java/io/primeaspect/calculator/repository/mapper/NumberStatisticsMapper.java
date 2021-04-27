package io.primeaspect.calculator.repository.mapper;

import io.primeaspect.calculator.model.Number;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NumberStatisticsMapper {
    @Insert("INSERT INTO number (value, count) VALUES " +
            "(#{number.value}, #{number.count})")
    void saveNumber(@Param("number") Number number);

    @Update("UPDATE number SET count=#{number.count} WHERE value=#{number.value}")
    void updateNumber(@Param("number") Number number);

    @Select("SELECT * FROM number WHERE count=(SELECT MAX(count) FROM number)")
    List<Number> getPopularNumber();

    @Select("SELECT * FROM number WHERE value=#{value}")
    List<Number> findNumber(double value);

    @Delete("DELETE FROM number")
    void deleteAllNumbers();
}