/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.stream;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

public final class TensorStreams {
    private TensorStreams() {

    }

    /**
     * Return a stream of all entries of the tensor
     * 
     * @param tensor
     * @return
     */
    public static <S> Stream<Map.Entry<Position, S>> tensorEntryStream(Tensor<S> tensor) {
        return tensor.asMap().entrySet().stream();

    }

    public static <T> TensorCollector<Map.Entry<Position, T>, T> toTensor() {
        return new TensorCollector<>(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <V, T> TensorCollector<V, T> toTensor(Function<V, Position> positionMapper,
            Function<V, T> valueMapper) {
        return new TensorCollector<>(positionMapper, valueMapper);
    }

}
