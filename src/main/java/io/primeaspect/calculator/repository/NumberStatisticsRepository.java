package io.primeaspect.calculator.repository;

import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.repository.mapper.NumberStatisticsMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NumberStatisticsRepository {
    private final NumberStatisticsMapper mapper;

    public NumberStatisticsRepository(NumberStatisticsMapper mapper) {
        this.mapper = mapper;
    }

    public void deleteAllNumbers() {
        mapper.deleteAllNumbers();
    }

    public Number getPopularNumber() {
        return mapper.getPopularNumber().get(0);
    }

    public void saveNumber(Number number) {
        mapper.saveNumber(number);
    }

    public void updateNumber(Number number) {
        mapper.updateNumber(number);
    }

    public List<Number> findNumber(double value) {
        return mapper.findNumber(value);
    }
}
