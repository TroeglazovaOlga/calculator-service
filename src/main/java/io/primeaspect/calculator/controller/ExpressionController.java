package io.primeaspect.calculator.controller;

import io.primeaspect.calculator.dto.request.ExpressionRequest;
import io.primeaspect.calculator.dto.response.ExpressionListResponse;
import io.primeaspect.calculator.exception.BadRequestException;
import io.primeaspect.calculator.model.Expression;
import io.primeaspect.calculator.service.ExpressionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expression")
public class ExpressionController {
    private final ExpressionService service;

    public ExpressionController(ExpressionService service) {
            this.service = service;
    }

    @PostMapping
    public Expression create(@RequestBody ExpressionRequest expression) throws BadRequestException {
        return service.create(expression);
    }

    @GetMapping
    public ExpressionListResponse findAll() {
        return service.findAll();
    }
}