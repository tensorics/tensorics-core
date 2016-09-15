/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.stream;

import java.util.Map;
import java.util.function.Function;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

public class TensorCollector<V, T> extends AbstractTensoricCollector<V, T, Tensor<T>> {

    public TensorCollector(Function<V, Position> positionMapper, Function<V, T> valueMapper) {
        super(positionMapper, valueMapper);
    }

    @Override
    public Function<Map<Position, T>, Tensor<T>> finisher() {
        return Tensorics::fromMap;
    }

}
