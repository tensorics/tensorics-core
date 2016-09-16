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

package org.tensorics.core.tensor.stream;

import java.util.Map;
import java.util.function.Function;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

/**
 * An {@link AbstractTensoricCollector} implementation to produce a generic tensor
 * 
 * @author mihostet 
 * @param <V> stream elements
 * @param <T> elements of the tensor (will build a Tensor<T>)
 */
public class TensorCollector<V, T> extends AbstractTensoricCollector<V, T, Tensor<T>> {

    public TensorCollector(Function<V, Position> positionMapper, Function<V, T> valueMapper) {
        super(positionMapper, valueMapper);
    }

    @Override
    public Function<Map<Position, T>, Tensor<T>> finisher() {
        return Tensorics::fromMap;
    }

}
