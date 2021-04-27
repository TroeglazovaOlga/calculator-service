package io.primeaspect.calculator.dto.response

import io.primeaspect.calculator.model.Expression

data class StatisticsResponse (
        val expression: Expression,
        val allExpressionsByDate: ExpressionListResponse,
        val popularNumber: NumberResponse
)