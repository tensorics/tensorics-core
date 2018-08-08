/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.reduction.ReductionStrategy;
import org.tensorics.core.tensor.Tensor;

public class OngoingTensorReduction<V> {

    public <C, R> Tensor<R> by(ReductionStrategy<C, V, R> strategy) {
        return reduceBy(strategy);
    }

    protected <C, R> Tensor<R> reduceBy(ReductionStrategy<C, V, R> strategy) {
        return null;// return new TensorReduction<>(dimension, strategy).apply(tensor);
    }

}
