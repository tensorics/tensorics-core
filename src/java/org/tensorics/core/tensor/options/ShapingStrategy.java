/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.options;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * Strategy for deciding on the shape of resulting tensor from the shapes of two tensors used for a binary operation.
 * 
 * @author agorzaws
 */
public interface ShapingStrategy extends ManipulationOption {

    /**
     * Resulting tensor shape of given two in following formula: FIRST_TENSOR_SHAPE x SECOND_TENSOR_SHAPE
     * 
     * @param first tensor
     * @param second tensor
     * @return resulting shape
     */
    <C> Shape shapeLeftRight(Tensor<?> first, Tensor<?> second);

}
