/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.lang;

import static org.tensorics.core.tensor.expressions.TensorExpressions.elementwise;
import static org.tensorics.core.tensorbacked.expressions.TensorbackedExpressions.extracted;
import static org.tensorics.core.tensorbacked.expressions.TensorbackedExpressions.wrapped;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tree.domain.Expression;

/**
 * Provides starting point methods for tensoric eDSL language expressions that describe expressions of tensors of
 * quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which all the operations are based on.
 */
public class QuantityTensorbackedExpressionSupport<V> {

    private final QuantityOperationRepository<V> operationRepository;

    public QuantityTensorbackedExpressionSupport(QuantityEnvironment<V> environment) {
        this.operationRepository = new QuantityOperationRepository<>(environment);
    }

    public final <QTB extends Tensorbacked<QuantifiedValue<V>>> Expression<QTB> elementNegativeOfTB(
            Class<QTB> resultClass, Expression<QTB> tensorbacked) {
        return wrapped(resultClass, elementwise(operationRepository.additiveInversion(), extracted(tensorbacked)));
    }

    public final <TB extends Tensorbacked<QuantifiedValue<V>>> OngoingDeferredQuantifiedTensorBackedOperation<V, TB> //
    calculateTB(Class<TB> resultClass, Expression<TB> left) {
        return new OngoingDeferredQuantifiedTensorBackedOperation<>(operationRepository, resultClass, left);
    }

}
