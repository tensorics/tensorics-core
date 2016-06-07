/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.base.Function;
import com.google.common.base.Functions;

public class FunctionExpressionSupport<Y> extends FunctionExpressionSupportWithConversion<Y, Y> {


    public FunctionExpressionSupport(Environment<Y> environment) {
        super(environment, Functions.identity());
    }

    public <X extends Comparable<? super X>> FunctionExpressionSupportWithConversion<X, Y> withConversion(
            Function<X, Y> conversion) {
        return new FunctionExpressionSupportWithConversion<>(environment(), conversion);
    }

    public final <Z extends Comparable<? super Z>> OngoingDeferredDiscreteFunctionOperation<Z, Z> calculateF(Expression<DiscreteFunction<Z, Z>> left) {
//        FunctionExpressionSupport<Z> support = toSupportOfComparable();
//        return new OngoingDeferredDiscreteFunctionOperation<Z, Z>(support.environment, left, Functions.identity());
        return null;
    }
//
//    public final Expression<Y> averageOfF(Expression<DiscreteFunction<?, Y>> function) {
//        ToYValues<Y> conversion = new ToYValues<>();
//        Expression<Iterable<Y>> yValues = new FunctionToIterableExpression<Y>(conversion, function);
//        return super.averageOf(yValues);
//    }
//
//    @SuppressWarnings("unchecked")
//    <Z extends Comparable<? super Z>> FunctionExpressionSupport<Z> toSupportOfComparable() {
//        try {
//            return (FunctionExpressionSupport<Z>) this;
//        } catch (ClassCastException exception) {
//            throw new IllegalStateException("The type of the FunctionSupport that you use MUST implement Comparable");
//        }
//    }

}
