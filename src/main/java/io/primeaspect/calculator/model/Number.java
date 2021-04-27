package io.primeaspect.calculator.model;

import java.util.Objects;

public class Number {
    private double value;
    private int count;

    public Number() {
    }

    public Number(double value) {
        this.value = value;
        this.count = 1;
    }

    public Number(double value, int count) {
        this.value = value;
        this.count = count;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number = (Number) o;
        return value == number.value && count == number.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, count);
    }
}
