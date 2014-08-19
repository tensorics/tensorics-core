/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.operations;

import org.tensorics.core.tensor.Tensor;

/**
 * Utility methods for tensors which are not exposed to the public API
 * 
 * @author kfuchsbe
 */
public final class TensorInternals {

    private TensorInternals() {
        /* only static methods */
    }

    public static <T> OngoingMapOut<T> mapOut(Tensor<T> tensor) {
        return new OngoingMapOut<>(tensor);
    }

}
