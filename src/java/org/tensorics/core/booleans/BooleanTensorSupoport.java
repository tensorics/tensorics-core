/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import static org.tensorics.core.booleans.operations.LogicalOperationsFactory.and;
import static org.tensorics.core.booleans.operations.LogicalOperationsFactory.nand;
import static org.tensorics.core.booleans.operations.LogicalOperationsFactory.or;
import static org.tensorics.core.booleans.operations.LogicalOperationsFactory.xor;

import org.tensorics.core.booleans.lang.OngoingBooleanAlgebra;
import org.tensorics.core.booleans.lang.OngoingDetection;
import org.tensorics.core.booleans.operations.LogicalOperation;
import org.tensorics.core.tensor.Tensor;

/**
 * A class (to be extended or instantiated) that provides an access to the basic Boolean operations.
 * 
 * @author agorzaws
 */
public class BooleanTensorSupoport {

    public static final LogicalOperation AND = and();
    public static final LogicalOperation NAND = nand();
    public static final LogicalOperation OR = or();
    public static final LogicalOperation XOR = xor();

    public OngoingBooleanAlgebra on(Tensor<Boolean> tensor) {
        return new OngoingBooleanAlgebra(tensor);
    }

    public OngoingDetection detect() {
        return new OngoingDetection();
    }

}
