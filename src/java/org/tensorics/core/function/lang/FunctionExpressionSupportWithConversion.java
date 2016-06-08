/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.expressions.FunctionToIterableExpression;
import org.tensorics.core.function.operations.ToYValues;
import org.tensorics.core.iterable.lang.ScalarIterableExpressionSupport;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.base.Function;

public class FunctionExpressionSupportWithConversion<X, Y> extends ScalarIterableExpressionSupport<Y> {

    private Environment<Y> environment;
    private ToYValues<X, Y> toYValues;
    private Function<X, Y> conversion;

    public FunctionExpressionSupportWithConversion(Environment<Y> environment, Function<X, Y> conversion) {
        super(environment.field());
        this.environment = environment;
        this.conversion = conversion;
        toYValues = new ToYValues<>();
    }

    // XXX: check that the cast is OK, otherwise throw an exception
    @SuppressWarnings("unchecked")
    public <Z extends Comparable<? super Z>> OngoingDeferredDiscreteFunctionOperation<Z, Y> calculateF(
            Expression<DiscreteFunction<Z, Y>> expression) {
        return new OngoingDeferredDiscreteFunctionOperation<>(environment, expression, (Function<Z, Y>) conversion);
    }

    public final Expression<Y> averageOfF(Expression<DiscreteFunction<X, Y>> function) {
        return averageOf(yValuesAsIterableExpression(function));
    }

    public final Expression<Y> rmsOfF(Expression<DiscreteFunction<X, Y>> function) {
        return rmsOf(yValuesAsIterableExpression(function));
    }

    private Expression<Iterable<Y>> yValuesAsIterableExpression(Expression<DiscreteFunction<X, Y>> function) {
        return new FunctionToIterableExpression<>(toYValues, function);
    }

    public Environment<Y> environment() {
        return environment;
    }
}
