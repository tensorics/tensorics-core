/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.functional.expressions;

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
public class FunctionalExpression<R> extends AbstractDeferredExpression<R> {

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

}
