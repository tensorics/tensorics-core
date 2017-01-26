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

import static org.tensorics.core.tensorbacked.Tensorbackeds.construct;

import java.util.Map;
import java.util.function.Function;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * An {@link AbstractTensoricCollector} implementation to collect to an arbitrary {@link Tensorbacked} class.
 * 
 * @author mihostet
 * @param <V> steam elements
 * @param <T> elements of the tensor in the tensorbacked
 * @param <TB> tensorbacked class to produce, must extend Tensorbacked<T>
 */
public class TensorbackedCollector<V, T, TB extends Tensorbacked<T>> extends AbstractTensoricCollector<V, T, TB> {

    private final Class<TB> tensorBackedClass;

    public TensorbackedCollector(Class<TB> tensorBackedClass, Function<V, Position> positionMapper,
            Function<V, T> valueMapper) {
        super(positionMapper, valueMapper);
        this.tensorBackedClass = tensorBackedClass;
    }

    @Override
    public Function<Map<Position, T>, TB> finisher() {
        return construct(tensorBackedClass)::fromMap;
    }

}
