package io.primeaspect.calculator.repository.number;

import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.repository.NumberStatisticsRepository;
import io.primeaspect.calculator.repository.mapper.NumberStatisticsMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NumberRepositoryUnitTest {
    private final NumberStatisticsMapper mapper = Mockito.mock(NumberStatisticsMapper.class);
    private final NumberStatisticsRepository repository = new NumberStatisticsRepository(mapper);

    @Test
    public void saveNumberTest() {
        Number request = new Number(1, 10);
        repository.saveNumber(request);
        verify(mapper).saveNumber(eq(request));
    }

    @Test
    public void updateNumberTest() {
        Number request = new Number(1, 10);
        repository.updateNumber(request);
        verify(mapper).updateNumber(eq(request));
    }

    @Test
    public void getPopularNumberTest() {
        Number expected = new Number(1, 10);
        List<Number> expectedList = new ArrayList<>();
        expectedList.add(expected);

        when(mapper.getPopularNumber()).thenReturn(expectedList);
        repository.getPopularNumber();

        verify(mapper).getPopularNumber();
    }
}
