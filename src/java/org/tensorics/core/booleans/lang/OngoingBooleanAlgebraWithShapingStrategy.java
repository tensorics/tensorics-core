/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.options.ShapingStrategy;

/**
 * @author agorzaws
 */
public class OngoingBooleanAlgebraWithShapingStrategy {

    private ShapingStrategy shapingStrategy;
    private Tensor<Boolean> tensor;
    private BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> logicalOperation;

    /**
     * @param tensor
     * @param logicOperation
     * @param shapingStrategy
     */
    public OngoingBooleanAlgebraWithShapingStrategy(Tensor<Boolean> tensor,
            BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> logicOperation, ShapingStrategy shapingStrategy) {
        this.tensor = tensor;
        this.shapingStrategy = shapingStrategy;
        this.logicalOperation = logicOperation;
    }

    /**
     * @param secondTensor
     * @return
     */
    public Tensor<Boolean> with(Tensor<Boolean> secondTensor) {
        return logicalOperation.perform(this.tensor, secondTensor);
    }
}
