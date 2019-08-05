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

package org.tensorics.core.tensorbacked;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import java.util.Map;
import java.util.Set;

import static org.tensorics.core.tensorbacked.TensorbackedInternals.createBackedByTensor;
import static org.tensorics.core.tensorbacked.TensorbackedInternals.dimensionsOf;

/**
 * A builder for tensor backed objects, which takes care that only positions which are compatible with the dimensions of
 * the foreseen underlaying tensor are put into it. Internally, it uses a builder for a tensor, to which most of the
 * methods are delegated. At build time, the tensor is encapsulated in the according tensor backed class.
 * <p>
 * This class is not thread safe.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the tensor (and thus also the tensor backed object)
 * @param <TB> the type of the tensor backed object
 */
public interface TensorbackedBuilder<V, TB extends Tensorbacked<V>> {

    TensorbackedBuilder<V, TB> put(Map.Entry<Position, V> entry);

    TensorbackedBuilder<V, TB> put(Position position, V value);

    TensorbackedBuilder<V, TB> putAll(Set<Map.Entry<Position, V>> entries);

    TensorbackedBuilder<V, TB> putAll(Position position, Map<Position, V> entries);

    TensorbackedBuilder<V, TB> putAll(Position position, Tensor<V> tensor);

    TensorbackedBuilder<V, TB> putAll(Position position, Tensorbacked<V> tensorbacked);

    TensorbackedBuilder<V, TB> putAll(TB tensorBacked);

    TensorbackedBuilder<V, TB> putAll(Tensor<V> tensor);

    TensorbackedBuilder<V, TB> putAll(Map<Position, V> newEntries);

    TensorbackedBuilder<V, TB> remove(Position position);

    TensorbackedBuilder<V, TB> context(Position context);

    TensorbackedBuilder<V, TB> context(Object... coordinates);

    /**
     * Builds the tensor backed object, after all the content is set.
     * 
     * @return a new instance of the tensor backed object, containing all the data as described after instantiating the
     *         builder.
     */
    TB build();

}
