/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.ImmutableTensor;
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
public class QuantityTensors {

    /**
     * private constructor to avoid instantiation
     */
    private QuantityTensors() {
        /* only static methods */
    }

    public static <S> Tensor<S> valuesOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<S> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<S>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().value());
        }
        return builder.build();
    }

    public static <S> Tensor<Optional<S>> errorsOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<Optional<S>> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<S>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().error());
        }
        return builder.build();
    }

    public static <S> Tensor<Boolean> validitiesOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<Boolean> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<S>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().validity());
        }
        return builder.build();
    }

    public static <S> Unit unitOf(Tensor<QuantifiedValue<S>> tensor) {
        for (Entry<QuantifiedValue<S>> one : tensor.entrySet()) {
            return one.getValue().unit();
        }
        throw new IllegalArgumentException("No entries in the given tensor! Cannot find out what is the unit.");
    }

    /**
     * Provides the way how to convert back the tensor of values into Quantified tensor of values
     * 
     * @param tensor
     * @param unit
     * @return
     */
    public static <S> Tensor<QuantifiedValue<S>> from(Tensor<S> tensor, Unit unit) {
        Builder<QuantifiedValue<S>> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<S> oneEntry : tensor.entrySet()) {
            builder.at(oneEntry.getPosition()).put(ImmutableQuantifiedValue.of(oneEntry.getValue(), unit));
        }
        return builder.build();
    }

}
