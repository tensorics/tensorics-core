/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

/**
 * A builder for discrete functions. It provides methods to put new values and to decide on the interpolation strategies
 * to use.
 * 
 * @author kfuchsbe
 * @param <X> the type of the values along the X-axis
 * @param <Y> the type of the values along the Y-axis
 */
public interface DiscreteFunctionBuilder<X extends Comparable<X>, Y> {

    DiscreteFunctionBuilder<X, Y> put(X key, Y value);

    DiscreteFunctionBuilder<X, Y> put(X key, Y value, Y error);

    DiscreteFunctionBuilder<X, Y> withInterpolationStrategy(InterpolationStrategy<X, Y> interpolationStrategy);

    DiscreteFunctionBuilder<X, Y> withName(String name);

    DiscreteFunction<X, Y> build();

}