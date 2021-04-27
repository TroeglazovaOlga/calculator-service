package io.primeaspect.calculator.dto.response

import io.primeaspect.calculator.model.Expression

data class ExpressionListResponse(
        val list: List<Expression> = listOf()
)