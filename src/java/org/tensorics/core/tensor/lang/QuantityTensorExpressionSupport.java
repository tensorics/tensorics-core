/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.iterable.lang.QuantityIterableExpressionSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides starting point methods for tensoric eDSL language expressions that describe expressions of tensors of
 * quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which all the operations are based on.
 */
public class QuantityTensorExpressionSupport<V> extends QuantityIterableExpressionSupport<V> {

    private final QuantityOperationRepository<V> quantityOperationRepository;

    public QuantityTensorExpressionSupport(QuantityEnvironment<V> environment) {
        super(environment);
        this.quantityOperationRepository = new QuantityOperationRepository<>(environment);
    }

    public OngoingDeferredQuantifiedTensorOperation<V> calculateV(Tensor<QuantifiedValue<V>> left) {
        return calculateT(ResolvedExpression.of(left));
    }

    public OngoingDeferredQuantifiedTensorOperation<V> calculateT(Expression<Tensor<QuantifiedValue<V>>> left) {
        return new OngoingDeferredQuantifiedTensorOperation<>(quantityOperationRepository, left);
    }

}
