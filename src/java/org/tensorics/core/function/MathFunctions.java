/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import org.tensorics.core.function.interpolation.InterpolationStrategy;
import org.tensorics.core.function.interpolation.SingleTypedInterpolationStrategy;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.reduction.ToFunctions;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

public class MathFunctions {

    public static <X, Y> Tensor<DiscreteFunction<X, Y>> functionsFrom(Tensor<Y> tensor, Class<X> dimensionClass) {
        Preconditions.checkArgument(tensor.shape().dimensionality() >= 1, "tensor must contain at least one dimension");
        return Tensorics.from(tensor).reduce(dimensionClass).by(new ToFunctions<>());
    }

    public static <X, Y> DiscreteFunction<X, Y> functionFrom1DTensor(Tensor<Y> tensor, Class<X> dimensionClass) {
        Preconditions.checkArgument(tensor.shape().dimensionality() == 1, "tensor must be one-dimensional");
        return functionsFrom(tensor, dimensionClass).get();
    }

    public static <X, V> SingleTypedDiscreteFunction<V> toSingleTyped(DiscreteFunction<X, V> function,
            Function<X, V> conversion) {
        Preconditions.checkNotNull(function, "function cannot be null");
        Preconditions.checkNotNull(conversion, "conversion cannot be null");

        MapBackedSingleTypedDiscreteFunction.Builder<V> builder = MapBackedSingleTypedDiscreteFunction.builder();

        for (X x : function.definedXValues()) {
            V yValue = function.apply(x);
            V newX = conversion.apply(x);
            builder.put(newX, yValue);
        }

        return builder.build();
    }

    public static <V extends Comparable<V>> SingleTypedInterpolatedFunction<V> interpolated(SingleTypedDiscreteFunction<V> function,
            SingleTypedInterpolationStrategy<V> strategy) {
        return new DefaultSingleTypedInterpolatedFunction<>(function, strategy);
    }

    public static <X extends Comparable<X>, Y> InterpolatedFunction<X, Y> interpolated(DiscreteFunction<X, Y> function,
            InterpolationStrategy<X, Y> strategy) {
        return new DefaultInterpolatedFunction<>(function, strategy);
    }

}
