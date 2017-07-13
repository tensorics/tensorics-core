/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.functional.expressions;

import java.io.Serializable;
import java.util.List;

import org.tensorics.core.functional.FiniteArgumentFunction;
import org.tensorics.core.functional.FuncN;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.collect.ImmutableList;

/**
 * Expression that evaluates using a {@link FuncN} function. This is a lambda-like expression.
 *
 * @see FiniteArgumentFunction
 * @param <R> the type of the result of the expression
 */
public class FunctionalExpression<R> extends AbstractDeferredExpression<R>implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Expression<?>> children;
    private final FuncN<R> func;

    public FunctionalExpression(List<Expression<?>> children, FuncN<R> func) {
        this.children = ImmutableList.copyOf(children);
        this.func = func;
    }

    @Override
    public List<Expression<?>> getChildren() {
        return this.children;
    }

    public FuncN<R> function() {
        return func;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((func == null) ? 0 : func.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FunctionalExpression<?> other = (FunctionalExpression<?>) obj;
        if (children == null) {
            if (other.children != null)
                return false;
        } else if (!children.equals(other.children))
            return false;
        if (func == null) {
            if (other.func != null)
                return false;
        } else if (!func.equals(other.func))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FunctionalExpression [children=" + children + ", func=" + func + "]";
    }

}
