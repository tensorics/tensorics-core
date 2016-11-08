/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;

public class OngoingBooleanAlgebra {

    private Tensor<Boolean> tensor;

    public OngoingBooleanAlgebra(Tensor<Boolean> tensor) {
        this.tensor = tensor;
    }

    public OngoingBooleanAlgebraForTensor apply(BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> operation) {
        return new OngoingBooleanAlgebraForTensor(this.tensor, operation);
    }

}
