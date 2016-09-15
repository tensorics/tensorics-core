/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.stream;

import java.util.Map;
import java.util.function.Function;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

public class TensorbackedCollector<V, T, TB extends Tensorbacked<T>> extends AbstractTensoricCollector<V, T, TB> {

    private final Class<TB> tensorBackedClass;

    public TensorbackedCollector(Class<TB> tensorBackedClass, Function<V, Position> positionMapper,
            Function<V, T> valueMapper) {
        super(positionMapper, valueMapper);
        this.tensorBackedClass = tensorBackedClass;
    }

    @Override
    public Function<Map<Position, T>, TB> finisher() {
        return map -> TensorbackedInternals.createBackedByTensor(tensorBackedClass, Tensorics.fromMap(map));

    }

}
