package io.primeaspect.calculator.model;

import java.time.LocalDate;
import java.util.Objects;

public class Expression {
    private LocalDate date;
    private String expression;
    private Double solution;

    public Expression() {
    }

    public Expression(LocalDate date, String expression, Double solution) {
        this.date = date;
        this.expression = expression;
        this.solution = solution;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Double getSolution() {
        return solution;
    }

    public void setSolution(Double solution) {
        this.solution = solution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expression that = (Expression) o;
        return Objects.equals(date, that.date) && Objects.equals(expression, that.expression) && Objects.equals(solution, that.solution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, expression, solution);
    }
}
