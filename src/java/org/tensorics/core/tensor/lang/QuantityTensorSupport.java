/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.iterable.lang.QuantityIterableSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensor.Entry;
import org.tensorics.core.units.Unit;

import com.google.common.base.Optional;

/**
 * Provides starting methods for tensoric eDSL expressions, which are related to tensors of quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values (elements of the field on which the operations are based on)
 */
public class QuantityTensorSupport<V> extends QuantityIterableSupport<V> {

    private final QuantityOperationRepository<V> pseudoField;

    public QuantityTensorSupport(QuantityEnvironment<V> environment) {
        super(environment);
        this.pseudoField = new QuantityOperationRepository<>(environment);
    }

    public OngoingQuantifiedTensorOperation<V> calculate(Tensor<QuantifiedValue<V>> left) {
        return new OngoingQuantifiedTensorOperation<>(pseudoField, left);
    }

    public Tensor<V> valuesOf(Tensor<QuantifiedValue<V>> tensor) {
        Builder<V> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<V>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().value());
        }
        return builder.build();
    }

    public Tensor<Optional<V>> errorsOf(Tensor<QuantifiedValue<V>> tensor) {
        Builder<Optional<V>> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<V>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().error());
        }
        return builder.build();
    }

    public Tensor<Boolean> validitiesOf(Tensor<QuantifiedValue<V>> tensor) {
        Builder<Boolean> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<V>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().validity());
        }
        return builder.build();
    }

    public Unit unitOf(Tensor<QuantifiedValue<V>> tensor) {
        for (Entry<QuantifiedValue<V>> one : tensor.entrySet()) {
            return one.getValue().unit();
        }
        throw new IllegalStateException("No entries in the given tensor! Cannot find out what is the unit.");
    }

}
