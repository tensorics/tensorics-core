/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.stream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

public class TensorCollector<T> implements Collector<Map.Entry<Position, T>, Map<Position, T>, Tensor<T>> {

    @Override
    public Supplier<Map<Position, T>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<Position, T>, Entry<Position, T>> accumulator() {
        return (map, entry) -> map.put(entry.getKey(), entry.getValue());
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
    public Function<Map<Position, T>, Tensor<T>> finisher() {
        return Tensorics::fromMap;
    }

    @Override
    public Set<java.util.stream.Collector.Characteristics> characteristics() {
        return Collections.singleton(Collector.Characteristics.UNORDERED);
    }

}
