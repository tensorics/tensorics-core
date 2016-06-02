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

public class MathFunctions {

    <X, Y> Tensor<DiscreteFunction<X, Y>> functionsFrom(Tensor<Y> tensor, Class<X> dimensionClass) {
        return Tensorics.from(tensor).reduce(dimensionClass).by(toFunctionsOf(dimensionClass));
    }

    private <Y, X> ReductionStrategy<X, Y, DiscreteFunction<X, Y>> toFunctionsOf(Class<X> dimensionClass) {
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

    <X, Y> DiscreteFunction<X, Y> functionFrom1DTensor(Tensor<Y> tensor, Class<X> dimensionClass) {
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
