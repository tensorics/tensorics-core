/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.operations;

import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementUnaryOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

/**
 * An operation which takes one tensor-backed object and returns another one of the same type.
 * 
 * @author agorzaws
 * @param <V> the type the values of the tensor
 * @param <TB> the type of the tensor backed object
 */
public class ElementTensorBackedUnaryOperation<V, TB extends Tensorbacked<V>> implements
        TensorBackedUnaryOperation<V, TB> {

    private final UnaryOperation<V> elementOperation;

    public ElementTensorBackedUnaryOperation(UnaryOperation<V> elementOperation) {
        super();
        this.elementOperation = elementOperation;
    }

    @Override
    public TB perform(TB value) {
        Tensor<V> internalTensor = new ElementUnaryOperation<V>(elementOperation).perform(value.tensor());
        /* safe cast since we ensure C as a type in the argument! */
        @SuppressWarnings("unchecked")
        Class<TB> tensorBackedClass = (Class<TB>) value.getClass();
        return TensorbackedInternals.createBackedByTensor(tensorBackedClass, internalTensor);
    }

}
