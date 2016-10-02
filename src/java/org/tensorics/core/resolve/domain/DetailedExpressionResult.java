/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.domain;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvingContext;

public class DetailedExpressionResult<R, E extends Expression<R>> {

    private final E rootExpression;
    private final R value;
    private final ResolvingContext context;

    private DetailedExpressionResult(E rootExpression, R value, ResolvingContext context) {
        super();
        this.rootExpression = requireNonNull(rootExpression, "rootExpression must not be null");
        this.value = requireNonNull(value, "value must not be null");
        this.context = requireNonNull(context, "context must not be null");
    }

    public static final <R, E extends Expression<R>> DetailedExpressionResult<R, E> of(E rootExpression, R value,
            ResolvingContext context) {
        return new DetailedExpressionResult<>(rootExpression, value, context);
    }

    public R value() {
        return value;
    }

    public ResolvingContext context() {
        return context;
    }

    public E rootExpression() {
        return this.rootExpression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((context == null) ? 0 : context.hashCode());
        result = prime * result + ((rootExpression == null) ? 0 : rootExpression.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DetailedExpressionResult<?, ?> other = (DetailedExpressionResult<?, ?>) obj;
        if (context == null) {
            if (other.context != null) {
                return false;
            }
        } else if (!context.equals(other.context)) {
            return false;
        }
        if (rootExpression == null) {
            if (other.rootExpression != null) {
                return false;
            }
        } else if (!rootExpression.equals(other.rootExpression)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetailedExpressionResult [rootExpression=" + rootExpression + ", value=" + value + ", context="
                + context + "]";
    }

}
