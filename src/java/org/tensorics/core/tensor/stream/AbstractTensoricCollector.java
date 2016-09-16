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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensor.Tensor;

/**
 * Abstract base class for a stream {@link Collector} which is backed by a map of {@link Position} to an arbitrary
 * value, from which a {@link Tensor} or {@link Tensorbacked} can be built in the finalization step.
 * 
 * @author mihostet 
 * @param <V> stream elements
 * @param <T> elements of the tensor to be produced
 * @param <O> output (e.g. Tensor<T> or Tensorbacked<T>)
 */
public abstract class AbstractTensoricCollector<V, T, O> implements Collector<V, Map<Position, T>, O> {

    private final Function<V, Position> positionMapper;
    private final Function<V, T> valueMapper;

    public AbstractTensoricCollector(Function<V, Position> positionMapper, Function<V, T> valueMapper) {
        this.positionMapper = positionMapper;
        this.valueMapper = valueMapper;
    }

    @Override
    public Supplier<Map<Position, T>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<Position, T>, V> accumulator() {
        return (map, entry) -> {
            Position position = positionMapper.apply(entry);
            if (map.put(position, valueMapper.apply(entry)) != null) {
                throw new IllegalStateException("duplicate entry for position " + position);
            }
        };
    }

    @Override
    public BinaryOperator<Map<Position, T>> combiner() {
        return (map1, map2) -> {
            Map<Position, T> mergedMap = new HashMap<>(map1);
            mergedMap.putAll(map2);
            return mergedMap;
        };
    }

    @Override
    public Set<java.util.stream.Collector.Characteristics> characteristics() {
        return Collections.singleton(Collector.Characteristics.UNORDERED);
    }

}
