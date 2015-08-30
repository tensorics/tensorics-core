/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensor.Entry;
import org.tensorics.core.units.Unit;

import com.google.common.base.Optional;

/**
 * Provides static utility methods for tensors with quantity values.
 * 
 * @author kfuchsbe
 */
public final class QuantityTensors {

    /**
     * private constructor to avoid instantiation
     */
    private QuantityTensors() {
        /* only static methods */
    }

    public static <S> Tensor<S> valuesOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<S> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : tensor.asMap().entrySet()) {
            builder.at(entry.getKey()).put(entry.getValue().value());
        }
        return builder.build();
    }

    public static <S> Tensor<Optional<S>> errorsOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<Optional<S>> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : tensor.asMap().entrySet()) {
            builder.at(entry.getKey()).put(entry.getValue().error());
        }
        return builder.build();
    }

    public static <S> Tensor<S> errorsOfOr(Tensor<QuantifiedValue<S>> tensor, S defaultValue) {
        Builder<S> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : tensor.asMap().entrySet()) {
            builder.at(entry.getKey()).put(entry.getValue().error().or(defaultValue));
        }
        return builder.build();
    }

    public static <S> Tensor<Boolean> validitiesOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<Boolean> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : tensor.asMap().entrySet()) {
            builder.at(entry.getKey()).put(entry.getValue().validity());
        }
        return builder.build();
    }

    public static <S> Unit unitOf(Tensor<QuantifiedValue<S>> tensor) {
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : tensor.asMap().entrySet()) {
            return entry.getValue().unit();
        }
        throw new IllegalArgumentException("No entries in the given tensor! Cannot find out what is the unit.");
    }

    public static <S> Tensor<QuantifiedValue<S>> quantityTensorOf(Tensor<S> tensor, Unit unit) {
        Builder<QuantifiedValue<S>> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (java.util.Map.Entry<Position, S> entry : tensor.asMap().entrySet()) {
            builder.at(entry.getKey()).put(ImmutableQuantifiedValue.of(entry.getValue(), unit));
        }
        return builder.build();
    }

}
