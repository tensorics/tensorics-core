/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

import com.google.common.base.Preconditions;

/**
 * A continuous function, generated from a discrete one together with an appropriate interpolation strategy.
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable (in)
 * @param <Y> the type of the dependent variable (out)
 */
public final class InterpolatedFunction<X extends Comparable<X>, Y> implements ContinuousFunction<X, Y> {

    private final DiscreteFunction<X, Y> discreteFunction;
    private final InterpolationStrategy<X, Y> interpolationStrategy;

    private InterpolatedFunction(DiscreteFunction<X, Y> function, InterpolationStrategy<X, Y> strategy) {
        this.interpolationStrategy = strategy;
        this.discreteFunction = function;
        Preconditions
                .checkArgument(discreteFunction != null, "Argument '" + "DiscreteFunction" + "' must not be null!");
        Preconditions.checkArgument(interpolationStrategy != null, "Argument '" + "InterpolationStrategy"
                + "' must not be null!");
    }

    @Override
    public Y getY(X xValue) {
        return interpolationStrategy.interpolate(xValue, discreteFunction);
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public static <X extends Comparable<X>, Y> InterpolatedFunction<X, Y> of(DiscreteFunction<X, Y> function,
            InterpolationStrategy<X, Y> strategy) {
        return new InterpolatedFunction<>(function, strategy);
    }

}
