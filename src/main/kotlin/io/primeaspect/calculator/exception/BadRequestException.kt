package io.primeaspect.calculator.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Expression is invalid")
class BadRequestException : Exception()
