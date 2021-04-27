package io.primeaspect.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Expression is invalid")
public class BadRequestException extends Exception {
    public BadRequestException() {
    }
}
