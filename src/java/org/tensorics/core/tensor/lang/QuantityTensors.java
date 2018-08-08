// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.tensor.lang;

import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorInternals;
import org.tensorics.core.units.JScienceUnit;
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
        builder.context(tensor.context());
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            builder.put(entry.getKey(), entry.getValue().value());
        }
        return builder.build();
    }

    public static <S> Tensor<Optional<S>> errorsOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<Optional<S>> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        builder.context(tensor.context());
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            builder.put(entry.getKey(), entry.getValue().error());
        }
        return builder.build();
    }

    public static <S> Tensor<S> errorsOfOr(Tensor<QuantifiedValue<S>> tensor, S defaultValue) {
        Builder<S> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        builder.context(tensor.context());
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            builder.put(entry.getKey(), entry.getValue().error().or(defaultValue));
        }
        return builder.build();
    }

    public static <S> Tensor<Boolean> validitiesOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<Boolean> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        builder.context(tensor.context());
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            builder.put(entry.getKey(), entry.getValue().validity());
        }
        return builder.build();
    }

    public static <S> Tensor<QuantifiedValue<S>> quantityTensorOf(Tensor<S> tensor, javax.measure.unit.Unit<?> unit) {
        return quantityTensorOf(tensor, JScienceUnit.of(unit));
    }

    public static <S> Tensor<QuantifiedValue<S>> quantityTensorOf(Tensor<S> tensor, Unit unit) {
        Builder<QuantifiedValue<S>> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        builder.context(tensor.context());
        for (java.util.Map.Entry<Position, S> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            builder.put(entry.getKey(), ImmutableQuantifiedValue.of(entry.getValue(), unit));
        }
        return builder.build();
    }

    public static <S> Unit unitOf(Tensor<QuantifiedValue<S>> tensor) {
        /*
         * XXX this is nasty! Even an empty tensor should have a correct base ...probably?
         */
        for (java.util.Map.Entry<Position, QuantifiedValue<S>> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            return entry.getValue().unit();
        }
        throw new IllegalArgumentException("No entries in the given tensor! Cannot find out what is the base.");
    }

}
