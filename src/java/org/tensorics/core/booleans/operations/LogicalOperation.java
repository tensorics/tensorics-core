/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.options.ShapingStrategy;

/**
 * Defines the logical operation between two {@link Tensor} of Boolean.
 * <p>
 * XXX: AG: I don't know if one need to extend the {@link BinaryFunction} here. To be discussed.
 * 
 * @author agorzaws
 */
public interface LogicalOperation extends BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> {

    /**
     * @param left tensor to perform the operation
     * @param right tensor to perform the operation
     * @param shapingStrategy to be used for the operation
     * @return resulting Tensor<> as defined by the {@link ShapingStrategy} with values described by the
     *         {@link LogicalOperation}
     */
    Tensor<Boolean> perform(Tensor<Boolean> left, Tensor<Boolean> right, ShapingStrategy shapingStrategy);

}
