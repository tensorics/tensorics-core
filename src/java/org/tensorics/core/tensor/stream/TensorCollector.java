/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

public class TensorCollector<V, T> implements Collector<V, Map<Position, T>, Tensor<T>> {

    private final Function<V, Position> positionMapper;
    private final Function<V, T> valueMapper;
    
    
    public TensorCollector(Function<V, Position> positionMapper, Function<V, T> valueMapper) {
        this.positionMapper = positionMapper;
        this.valueMapper = valueMapper;
    }

    @Override
    public Supplier<Map<Position, T>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<Position, T>, V> accumulator() {
        return (map, entry) -> map.put(positionMapper.apply(entry), valueMapper.apply(entry));
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
