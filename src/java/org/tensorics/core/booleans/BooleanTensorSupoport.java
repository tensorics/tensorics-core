/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import static org.tensorics.core.booleans.operations.LogicalTensorOperationsFactory.and;
import static org.tensorics.core.booleans.operations.LogicalTensorOperationsFactory.nand;
import static org.tensorics.core.booleans.operations.LogicalTensorOperationsFactory.or;
import static org.tensorics.core.booleans.operations.LogicalTensorOperationsFactory.xor;

import org.tensorics.core.booleans.lang.OngoingBooleanAlgebra;
import org.tensorics.core.booleans.lang.OngoingDetection;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;

/**
 * A class (to be extended or instantiated) that provides an access to the basic Boolean operations.
 * 
 * @author agorzaws
 */
public class BooleanTensorSupoport {

    public static final BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> AND = and();
    public static final BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> OR = or();
    public static final BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> XOR = xor();
    public static final BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> NAND = nand();

    public OngoingBooleanAlgebra on(Tensor<Boolean> tensor) {
        return new OngoingBooleanAlgebra(tensor);
    }

    public OngoingDetection detect() {
        return new OngoingDetection();
    }

}
