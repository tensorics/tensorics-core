/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.lang;

import static org.tensorics.core.tensorbacked.TensorbackedInternals.createBackedByTensor;

import org.tensorics.core.commons.lang.OngoingOperation;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * Part of the tensorics fluent API that provides methods to describe the right hand part of binary operations on tensor
 * backed instances, containing quantified values.
 * 
 * @author agorzaws
 * @param <QTB> the type of the quantified tensor backed instances
 * @param <S> the type of the scalars (elements of the field on which all the operations are based on)
 */
public class OngoingQuantifiedTensorBackedOperation<QTB extends Tensorbacked<QuantifiedValue<S>>, S> implements
        OngoingOperation<QTB, QuantifiedValue<S>> {

    private final QuantityOperationRepository<S> pseudoField;
    private final QTB left;

    public OngoingQuantifiedTensorBackedOperation(QuantityOperationRepository<S> pseudoField, QTB left) {
        super();
        this.pseudoField = pseudoField;
        this.left = left;
    }

    @Override
    public QTB plus(QTB right) {
        return evaluate(right.tensor(), pseudoField.addition());
    }

    @Override
    public QTB minus(QTB right) {
        return evaluate(right.tensor(), pseudoField.subtraction());
    }

    @Override
    public QTB elementTimes(QTB right) {
        return evaluate(right.tensor(), pseudoField.multiplication());
    }

    @Override
    public QTB elementTimesV(QuantifiedValue<S> right) {
        return elementTimesQT(Tensorics.zeroDimensionalOf(right));
    }

    public QTB elementTimesQT(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, pseudoField.multiplication());
    }

    @Override
    public QTB elementDividedBy(QTB right) {
        return evaluate(right.tensor(), pseudoField.division());
    }

    public QTB elementDividedByQT(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, pseudoField.division());
    }

    @Override
    public QTB elementDividedByV(QuantifiedValue<S> value) {
        return elementDividedByQT(Tensorics.zeroDimensionalOf(value));
    }

    private QTB evaluate(Tensor<QuantifiedValue<S>> right, BinaryOperation<QuantifiedValue<S>> operation) {
        Tensor<QuantifiedValue<S>> perform = new ElementBinaryOperation<>(operation, pseudoField.environment()
                .options()).perform(left.tensor(), right);
        return createBackedByTensor(classOf(left), perform);
    }

    @SuppressWarnings("unchecked")
    private Class<QTB> classOf(QTB tensorBacked) {
        return (Class<QTB>) tensorBacked.getClass();
    }

}
