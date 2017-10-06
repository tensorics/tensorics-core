/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import java.util.Arrays;

import org.tensorics.core.booleans.operations.And;
import org.tensorics.core.booleans.operations.Or;
import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.iterable.expressions.IterableExpressionToIterable;
import org.tensorics.core.tree.domain.Expression;

public final class TensoricBooleanExpressions {

    private static final And AND = new And();
    private static final Or OR = new Or();

    private TensoricBooleanExpressions() {
        /* Only static methods */
    }

    public static Expression<Boolean> or(Expression<? extends Iterable<Boolean>> sources) {
        return new ConversionOperationExpression<>(OR, sources);
    }

    @SafeVarargs
    public static Expression<Boolean> or(Expression<Boolean>... expressions) {
        return or(Arrays.asList(expressions));
    }

    public static Expression<Boolean> or(Iterable<? extends Expression<Boolean>> expressions) {
        Expression<Iterable<Boolean>> booleans = new IterableExpressionToIterable<>(expressions);
        return or(booleans);
    }

    public static Expression<Boolean> and(Expression<? extends Iterable<Boolean>> sources) {
        return new ConversionOperationExpression<>(AND, sources);
    }

    public static Expression<Boolean> and(Iterable<? extends Expression<Boolean>> expressions) {
        Expression<Iterable<Boolean>> booleans = new IterableExpressionToIterable<>(expressions);
        return and(booleans);
    }

    @SafeVarargs
    public static Expression<Boolean> and(Expression<Boolean>... expressions) {
        return and(Arrays.asList(expressions));
    }

}
