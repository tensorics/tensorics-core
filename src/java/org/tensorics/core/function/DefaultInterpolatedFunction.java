/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import java.util.Set;

import org.tensorics.core.function.interpolation.InterpolationStrategy;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

/**
 * @author caguiler
 * @param <X>
 * @param <Y>
 */
public class DefaultInterpolatedFunction<X extends Comparable<? super X>, Y> implements InterpolatedFunction<X, Y> {

    private DiscreteFunction<X, Y> backingFunction;
    private InterpolationStrategy<Y> strategy;
    private Function<X,Y> conversion;

    public DefaultInterpolatedFunction(DiscreteFunction<X, Y> function, InterpolationStrategy<Y> strategy,
            Function<X, Y> conversion) {
        Preconditions.checkNotNull(function, "function cannot be null");
        Preconditions.checkNotNull(strategy, "strategy cannot be null");
        this.backingFunction = function;
        this.strategy = strategy;
        this.conversion = conversion;
    }

    @Override
    public Y apply(X input) {
        if (backingFunction.definedXValues().contains(input)) {
            return backingFunction.apply(input);
        }
        return strategy.interpolate(input, backingFunction, conversion);
    }

    @Override
    public Set<X> definedXValues() {
        return backingFunction.definedXValues();
    }

}
