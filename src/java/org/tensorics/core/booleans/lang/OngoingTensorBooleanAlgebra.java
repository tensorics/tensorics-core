/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;

public class OngoingTensorBooleanAlgebra {

    private Tensor<Boolean> tensor;

    public OngoingTensorBooleanAlgebra(Tensor<Boolean> tensor) {
        this.tensor = tensor;
    }

    public OngoingTensorAwareBooleanAlgebra apply(BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> operation) {
        return new OngoingTensorAwareBooleanAlgebra(this.tensor, operation);
    }

}
