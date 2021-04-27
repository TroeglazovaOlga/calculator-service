package io.primeaspect.calculator.service;

import io.primeaspect.calculator.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorServiceTest {
    CalculatorService service = new CalculatorService();

    @ParameterizedTest
    @CsvSource({"(-78+9-(9/4.5))^2,5041.0",
            "9*1+4.5,13.5",
            "9*(-1)+4.5,-4.5",
            "-1+(6+10-4)/(1+1*2),3",
            "-(-4+6+10)/(1+1*2),-4"})
    public void calculateTest(String expression, String res) throws BadRequestException {
        Double result = service.calculate(expression);
        Assertions.assertEquals(result, Double.parseDouble(res));
    }

    @ParameterizedTest
    @CsvSource({"(-78++9-(9/4.5))^2",
            "9-*1+4.5",
            "*9*(-1)+4.5",
            ")-1+(6+10-4)/(1+1*2)",
            "-(-4+6+10)/(1+1*2)+"})
    public void expectedBadRequestExceptionTest(String expression) {
        Assertions.assertThrows(BadRequestException.class, () -> service.calculate(expression));
    }
}