/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.stream;

import java.util.Map;
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

    public static <T> TensorCollector<T> toTensor() {
        return new TensorCollector<>();
    }

}
