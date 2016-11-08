/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.options.ExactShapesOrOneZeroStrategy;
import org.tensorics.core.tensor.options.ShapingStrategy;

/**
 * @author agorzaws
 */
public class OngoingBooleanAlgebraForTensor {

    private BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> logicOperation;
    private Tensor<Boolean> tensor;

    /**
     * @param tensor
     * @param logicOperation
     */
    public OngoingBooleanAlgebraForTensor(Tensor<Boolean> tensor,
            BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> logicOperation) {
        this.logicOperation = logicOperation;
        this.tensor = tensor;
    }

    /**
     * Performs the provided logical operation with a default shaping strategy {@link ExactShapesOrOneZeroStrategy}.
     * 
     * @param tensorToWorkOn
     * @return result of the operation
     */
    public Tensor<Boolean> with(Tensor<Boolean> tensorToWorkOn) {
        return logicOperation.perform(tensorToWorkOn, this.tensor);
    }

    /**
     * Defines the shaping strategy for the operation
     * 
     * @param shapingStrategy
     * @return
     */
    public OngoingBooleanAlgebraWithShapingStrategy withShaping(ShapingStrategy shapingStrategy) {
        throw new UnsupportedOperationException("not implemented yet");
        // return new OngoingBooleanAlgebraWithShapingStrategy(tensor, logicOperation, shapingStrategy);
    }
}
