/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.tensor.Tensor;

/**
 * Structural manipulations of tensors (which do not require any knowledge about the mathematical behaviour of the
 * elements).
 * 
 * @author kfuchsbe
 */
public final class TensorStructurals {

    /**
     * Private constructor to avoid instantiation
     */
    private TensorStructurals() {
        /* only static methods */
    }

    public static <V> OngoingTensorManipulation<V> from(Tensor<V> tensor) {
        return new OngoingTensorManipulation<>(tensor);
    }

}
