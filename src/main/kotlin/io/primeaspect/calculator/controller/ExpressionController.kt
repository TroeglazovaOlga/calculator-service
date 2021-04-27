package io.primeaspect.calculator.controller

import io.primeaspect.calculator.dto.request.ExpressionRequest
import io.primeaspect.calculator.service.ExpressionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expression")
class ExpressionController(
    private val service: ExpressionService
) {
    @PostMapping
    fun create(
        @RequestBody
        expression: ExpressionRequest
    ) = service.create(expression)

    @GetMapping
    fun findAll() = service.findAll()
}
