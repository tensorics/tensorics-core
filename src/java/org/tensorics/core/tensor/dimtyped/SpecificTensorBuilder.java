package org.tensorics.core.tensor.dimtyped;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;

import java.util.Map;

public interface SpecificTensorBuilder<V, B extends SpecificTensorBuilder<V, B>> extends TensorBuilder<V> {

    @Override
    B context(Position context);

    @Override
    B putAll(Tensor<V> tensor);

    @Override
    B putAll(Position position, Tensor<V> tensor);

    @Override
    B put(Position position, V value);

    @Override
    B put(Map.Entry<Position, V> entry);

    @Override
    B remove(Position position);

    @Override
    B putAll(Map<Position, V> newEntries);

    @Override
    B putAll(Position position, Map<Position, V> map);

}
