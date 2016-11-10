/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingDetection;
import org.tensorics.core.booleans.lang.OngoingTensorBooleanAlgebra;
import org.tensorics.core.tensor.Tensor;

/**
 * A class (to be extended or instantiated) that provides an access to the basic Boolean operations.
 * 
 * @author agorzaws
 */
public class TensorBooleanSupport {

    public OngoingTensorBooleanAlgebra calcLogical(Tensor<Boolean> tensor) {
        return new OngoingTensorBooleanAlgebra(tensor);
    }

    public OngoingDetection detect() {
        return new OngoingDetection();
    }

}
