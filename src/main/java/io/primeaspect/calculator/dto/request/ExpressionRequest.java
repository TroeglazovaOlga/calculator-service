package io.primeaspect.calculator.dto.request;

import java.util.Objects;

public class ExpressionRequest {
    private String expression;

    public ExpressionRequest() {
    }

    public ExpressionRequest(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressionRequest request = (ExpressionRequest) o;
        return Objects.equals(expression, request.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
