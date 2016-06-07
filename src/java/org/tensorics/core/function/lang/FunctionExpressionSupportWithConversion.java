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
    private Function<X, Y> conversion;

    public FunctionExpressionSupportWithConversion(Environment<Y> environment, Function<X, Y> conversion) {
        super(environment.field());
        this.environment = environment;
        this.conversion = conversion;
    }

    public final Expression<Y> averageOfF(Expression<DiscreteFunction<?, Y>> function) {
        Expression<Iterable<Y>> yValues = new FunctionToIterableExpression<>(new ToYValues<>(), function);
        return super.averageOf(yValues);
    }

    public Environment<Y> environment() {
        return environment;
    }

}
