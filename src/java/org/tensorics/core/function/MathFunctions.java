/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Function;

public class MathFunctions {

    <X, Y> Tensor<DiscreteFunction<X, Y>> functionsFrom(Tensor<Y> tensor, Class<X> dimensionClass) {
        return null;
    }

    <X, Y> DiscreteFunction<X, Y> functionFrom1DTensor(Tensor<Y> tensor, Class<X> dimensionClass) {
        return functionsFrom(tensor, dimensionClass).get();
    }

    <V, Y> SingleTypedDiscreteFunction<V> toSingleTyped(DiscreteFunction<V, Y> function, Function<Y, V> conversion) {
        return null;
    }

    <V> SingleTypedContinuousFunction<V> toContinuousSingleType(SingleTypedDiscreteFunction<V> function,
            SingleTypedInterpolationStrategy<V> strategy) {
        return null;
    }

    // not sure if this makes sense
    <X, Y> ContinuousFunction<X, Y> toContinuous(DiscreteFunction<X, Y> function,
            InterpolationStrategy<X, Y> strategy) {
        return null;
    }

}
