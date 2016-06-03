/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import java.util.Map;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.reduction.ReductionStrategy;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

public class MathFunctions {

    public static <X, Y> Tensor<DiscreteFunction<X, Y>> functionsFrom(Tensor<Y> tensor, Class<X> dimensionClass) {
        Preconditions.checkArgument(tensor.shape().dimensionality() >= 1, "tensor must contain at least one dimension");
        return Tensorics.from(tensor).reduce(dimensionClass).by(toFunctionsOf(dimensionClass));
    }

    private static <Y, X> ReductionStrategy<X, Y, DiscreteFunction<X, Y>> toFunctionsOf(Class<X> dimensionClass) {
        return new ReductionStrategy<X, Y, DiscreteFunction<X, Y>>() {

            @Override
            public DiscreteFunction<X, Y> reduce(Map<? extends X, Y> inputValues, Position position) {
                return MapBackedDiscreteFunction.fromMap(inputValues);
            }

            @Override
            public Position context(Position originalContext) {
                return originalContext;
            }

        };
    }

    public static <X, Y> DiscreteFunction<X, Y> functionFrom1DTensor(Tensor<Y> tensor, Class<X> dimensionClass) {
        Preconditions.checkArgument(tensor.shape().dimensionality() == 1, "tensor must be one-dimensional");
        return functionsFrom(tensor, dimensionClass).get();
    }

    <V, Y> SingleTypedDiscreteFunction<V> toSingleTyped(DiscreteFunction<V, Y> function, Function<Y, V> conversion) {
        return null;
    }

    <V> SingleTypedInterpolatedFunction<V> interpolated(SingleTypedDiscreteFunction<V> function,
            SingleTypedInterpolationStrategy<V> strategy) {
        return null;
    }

    // not sure if this makes sense
    <X, Y> InterpolatedFunction<X, Y> interpolated(DiscreteFunction<X, Y> function,
            InterpolationStrategy<X, Y> strategy) {
        return null;
    }

}
