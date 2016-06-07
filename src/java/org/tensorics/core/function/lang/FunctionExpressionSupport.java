/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.base.Function;
import com.google.common.base.Functions;

public class FunctionExpressionSupport<Y> {

    private Environment<Y> environment;

    public FunctionExpressionSupport(Environment<Y> environment) {
        this.environment = environment;
    }

    public <X extends Comparable<? super X>> FunctionExpressionSupportWithConversion<X, Y> withConversion(
            Function<X, Y> conversion) {
        return new FunctionExpressionSupportWithConversion<>(environment, conversion);
    }

    public final <Z extends Comparable<? super Z>> OngoingDeferredDiscreteFunctionOperation<Z, Z> calculate(
            Expression<DiscreteFunction<Z, Z>> left) {
        FunctionExpressionSupport<Z> support = toSupportOfComparable();
        return new OngoingDeferredDiscreteFunctionOperation<Z, Z>(support.environment, left, Functions.identity());
    }

    @SuppressWarnings("unchecked")
    <Z extends Comparable<? super Z>> FunctionExpressionSupport<Z> toSupportOfComparable() {
        try {
            return (FunctionExpressionSupport<Z>) this;
        } catch (ClassCastException exception) {
            throw new IllegalStateException("The type of the FunctionSupport that you use MUST implement Comparable");
        }
    }
}
