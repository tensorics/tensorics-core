/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.lang;

import org.tensorics.core.iterable.lang.QuantityIterableSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementUnaryOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

/**
 * Provides starting methods for tensoric eDSL expressions, which are related to tensors of quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values (elements of the field on which the operations are based on)
 */
public class QuantityTensorbackedSupport<V> extends QuantityIterableSupport<V> {

    private final QuantityOperationRepository<V> pseudoField;

    public QuantityTensorbackedSupport(QuantityEnvironment<V> environment) {
        super(environment);
        this.pseudoField = new QuantityOperationRepository<>(environment);
    }

    public <QTB extends Tensorbacked<QuantifiedValue<V>>> OngoingQuantifiedTensorBackedOperation<QTB, V> calculate(
            QTB left) {
        return new OngoingQuantifiedTensorBackedOperation<>(pseudoField, left);
    }

    public <QTB extends Tensorbacked<QuantifiedValue<V>>> QTB negativeOf(QTB tensorBacked) {
        Tensor<QuantifiedValue<V>> perform = new ElementUnaryOperation<>(pseudoField.additiveInversion())
                .perform(tensorBacked.tensor());
        /* safe cast since we ensure QTB as a type in the argument! */
        @SuppressWarnings("unchecked")
        Class<QTB> tensorBackedClass = (Class<QTB>) tensorBacked.getClass();
        return TensorbackedInternals.createBackedByTensor(tensorBackedClass, perform);
    }

}
