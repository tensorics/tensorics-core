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
 * @param <S> the type of the scalar values (elements of the field on which the operations are based on)
 */
public class QuantityTensorSupport<S> extends QuantityIterableSupport<S> {

    private final QuantityOperationRepository<S> pseudoField;

    public QuantityTensorSupport(QuantityEnvironment<S> environment) {
        super(environment);
        this.pseudoField = new QuantityOperationRepository<>(environment);
    }

    public OngoingQuantifiedTensorOperation<S> calculate(Tensor<QuantifiedValue<S>> left) {
        return new OngoingQuantifiedTensorOperation<>(pseudoField, left);
    }

    public Tensor<S> valuesOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<S> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<S>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().value());
        }
        return builder.build();
    }

    public Tensor<Optional<S>> errorsOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<Optional<S>> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<S>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().error());
        }
        return builder.build();
    }

    public Tensor<Boolean> validitiesOf(Tensor<QuantifiedValue<S>> tensor) {
        Builder<Boolean> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (Entry<QuantifiedValue<S>> one : tensor.entrySet()) {
            builder.at(one.getPosition()).put(one.getValue().validity());
        }
        return builder.build();
    }

    public Unit unitOf(Tensor<QuantifiedValue<S>> tensor) {
        for (Entry<QuantifiedValue<S>> one : tensor.entrySet()) {
            return one.getValue().unit();
        }
        throw new IllegalStateException("No entries in the given tensor! Cannot find out what is the unit.");
    }

}
