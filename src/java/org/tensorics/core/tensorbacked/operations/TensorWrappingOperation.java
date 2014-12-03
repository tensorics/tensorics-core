/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.operations;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

/**
 * The default implementation of an operation, which wraps a tensor into a tensorbacked object of a given type.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the tensors and tensorbacked objects
 * @param <TB> the type of the tensorbacked objects into which tensors will be wrapped
 */
public class TensorWrappingOperation<V, TB extends Tensorbacked<V>> implements Conversion<Tensor<V>, TB> {

    private final Class<TB> tensorbackedClass;

    /**
     * The constructor, which requires a class which can wrap tensors
     * 
     * @param tensorbackedClass the type of the tensorbacked objects that will be created
     */
    public TensorWrappingOperation(Class<TB> tensorbackedClass) {
        super();
        this.tensorbackedClass = tensorbackedClass;
    }

    @Override
    public TB perform(Tensor<V> tensor) {
        return TensorbackedInternals.createBackedByTensor(tensorbackedClass, tensor);
    }

}
