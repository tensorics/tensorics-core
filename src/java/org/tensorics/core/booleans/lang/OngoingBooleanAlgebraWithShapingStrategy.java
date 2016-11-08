/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import org.tensorics.core.booleans.operations.LogicalOperation;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.options.ShapingStrategy;

/**
 * @author agorzaws
 */
public class OngoingBooleanAlgebraWithShapingStrategy {

    private ShapingStrategy shapingStrategy;
    private Tensor<Boolean> tensor;
    private LogicalOperation logicOperation;

    /**
     * @param tensor
     * @param logicOperation
     * @param shapingStrategy
     */
    public OngoingBooleanAlgebraWithShapingStrategy(Tensor<Boolean> tensor, LogicalOperation logicOperation,
            ShapingStrategy shapingStrategy) {
        this.tensor = tensor;
        this.shapingStrategy = shapingStrategy;
        this.logicOperation = logicOperation;
    }

    /**
     * Perform the provieded logical operation with defined {@link ShapingStrategy}.
     * 
     * @param secondTensor
     * @return
     */
    public Tensor<Boolean> with(Tensor<Boolean> secondTensor) {
        return logicOperation.perform(this.tensor, secondTensor, shapingStrategy);
    }
}
