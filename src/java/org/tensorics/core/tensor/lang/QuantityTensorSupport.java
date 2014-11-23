/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.iterable.lang.QuantityIterableSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tensor.Tensor;

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

}
