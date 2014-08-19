/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.options;

import java.util.Set;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorPair;

/**
 * Implementations of this strategy type define how tensors shall be treated, if their dimensions do not match. A
 * broadcasting strategy has to take care, that the two returned tensors have the same dimensions, but still might have
 * different shape.
 * 
 * @author kfuchsbe
 */
public interface BroadcastingStrategy extends ManipulationOption {

    /**
     * Has to broadcast the given to tensors into a new pair of tensors, that are consistent according to the
     * broadcasting strategy in question.
     * 
     * @param left the left tensor to broadcast
     * @param right the right tensor to broadcast
     * @param excludedDimensions a set of dimensions, which should be excluded from broadcasting
     * @return the result of the broadcasting
     */
    <V> TensorPair<V> broadcast(Tensor<V> left, Tensor<V> right, Set<Class<?>> excludedDimensions);
    
}
