/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.commons.lang.OngoingOperation;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;

/**
 * Part of the tensorics fluent API that provides methods to describe the right hand part of binary operations on
 * tensors containing quantified values.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (elements of the field on which all the operations are based on)
 */
public class OngoingQuantifiedTensorOperation<S> implements
        OngoingOperation<Tensor<QuantifiedValue<S>>, QuantifiedValue<S>> {

    private final QuantityOperationRepository<S> operationRepository;
    private final Tensor<QuantifiedValue<S>> left;

    public OngoingQuantifiedTensorOperation(QuantityOperationRepository<S> operationRepository,
            Tensor<QuantifiedValue<S>> left) {
        super();
        this.operationRepository = operationRepository;
        this.left = left;
    }

    @Override
    public Tensor<QuantifiedValue<S>> plus(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, operationRepository.addition());
    }

    @Override
    public Tensor<QuantifiedValue<S>> minus(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, operationRepository.subtraction());
    }

    @Override
    public Tensor<QuantifiedValue<S>> elementTimes(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, operationRepository.multiplication());
    }

    @Override
    public Tensor<QuantifiedValue<S>> elementTimesV(QuantifiedValue<S> right) {
        return elementTimes(ImmutableTensor.zeroDimensionalOf(right));
    }

    @Override
    public Tensor<QuantifiedValue<S>> elementDividedBy(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, operationRepository.division());
    }

    @Override
    public Tensor<QuantifiedValue<S>> elementDividedByV(QuantifiedValue<S> value) {
        Tensor<QuantifiedValue<S>> right = ImmutableTensor.zeroDimensionalOf(value);
        return elementDividedBy(right);
    }

    private Tensor<QuantifiedValue<S>> evaluate(Tensor<QuantifiedValue<S>> right,
            BinaryOperation<QuantifiedValue<S>> operation) {
        return new ElementBinaryOperation<>(operation, operationRepository.environment().options())
                .perform(left, right);
    }
}
