/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.operations.DiscreteFunctionAddition;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.base.Function;

public class OngoingDeferredDiscreteFunctionOperation<X extends Comparable<? super X>, Y> {

    private final Environment<Y> environment;
    private final Function<X, Y> conversion;
    private final Expression<DiscreteFunction<X, Y>> left;

    public OngoingDeferredDiscreteFunctionOperation(Environment<Y> environment, Expression<DiscreteFunction<X, Y>> left,
            Function<X, Y> conversion) {
        this.environment = environment;
        this.conversion = conversion;
        this.left = left;
    }

    public final Expression<DiscreteFunction<X, Y>> plus(Expression<DiscreteFunction<X, Y>> right) {
        return new BinaryOperationExpression<>(new DiscreteFunctionAddition<>(environment, conversion), left, right);
    }

    public Expression<Y> averageOf(Expression<DiscreteFunction<X, Y>> function) {
        return new FunctionExpressionSupportWithConversion<>(environment, conversion).averageOf(function);
    }

}
