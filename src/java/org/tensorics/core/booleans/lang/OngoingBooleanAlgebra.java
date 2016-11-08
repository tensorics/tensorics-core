/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import org.tensorics.core.booleans.operations.LogicalOperation;
import org.tensorics.core.tensor.Tensor;

public class OngoingBooleanAlgebra {

    private Tensor<Boolean> tensor;

    public OngoingBooleanAlgebra(Tensor<Boolean> tensor) {
        this.tensor = tensor;
    }

    public OngoingBooleanAlgebraForTensor apply(LogicalOperation logicOperation) {
        return new OngoingBooleanAlgebraForTensor(this.tensor, logicOperation);
    }
    

}
