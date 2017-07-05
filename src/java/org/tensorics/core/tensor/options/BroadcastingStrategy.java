// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

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
