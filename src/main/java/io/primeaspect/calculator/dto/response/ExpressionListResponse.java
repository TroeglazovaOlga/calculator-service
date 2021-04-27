package io.primeaspect.calculator.dto.response;

import io.primeaspect.calculator.model.Expression;

import java.util.List;
import java.util.Objects;

public class ExpressionListResponse {
    private List<Expression> list;

    public ExpressionListResponse() {
    }

    public ExpressionListResponse(List<Expression> list) {
        this.list = list;
    }

    public List<Expression> getList() {
        return list;
    }

    public void setList(List<Expression> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressionListResponse that = (ExpressionListResponse) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
