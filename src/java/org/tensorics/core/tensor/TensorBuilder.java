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
package org.tensorics.core.tensor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The interface any builder of a tensor has to implement
 * 
 * @author kfuchsbe
 * @param <E> the type of the tensor values
 */
public interface TensorBuilder<E> {

    void putAt(E value, Position position);

    void putAt(E value, Object... coordinates);

    void putAt(E value, Set<?> coordinates);

    void removeAt(Position position);

    void setTensorContext(Context context);

    /**
     * Puts all the values of the given tensor into the new tensor, at the given position. The positions in the new
     * tensor will be the merged positions of the original coordinates in the tensor with the given target position.
     * Therefore, the given position is not allowed to have a dimensions overlap with the positions in the original
     * tensor.
     * 
     * @param tensor the tensor, whose values to add to the tensor under construction
     * @param position the position which will be merged with the tensor in the source tensor
     */
    void putAll(Tensor<E> tensor);

    void putAllAt(Tensor<E> tensor, Position position);

    /**
     * Puts all the values of the given tensor into the new tensor at a position represented by the given coordinates.
     * This is a convenience method for {@link #putAllAt(Tensor, Position)}.
     * 
     * @param tensor the tensor whose values to put into the tensor unser construction
     * @param coordinates the coordinates defining the position where to put the values
     */
    void putAllAt(Tensor<E> tensor, Object... coordinates);

    void putAllAt(Tensor<E> tensor, Set<?> coordinates);

    void put(Entry<Position, E> entry);

    void putAllMap(Map<Position, E> newEntries);

    Tensor<E> build();

}
