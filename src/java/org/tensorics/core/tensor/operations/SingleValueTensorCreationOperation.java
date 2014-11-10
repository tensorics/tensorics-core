package org.tensorics.core.tensor.operations;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.CreationOperation;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * Uses one value and a given shape to create a tensor, which has the same values at all positions.
 * 
 * @author kaifox
 * @param <V> the type of the elements of the tensor to be created
 */
public class SingleValueTensorCreationOperation<V> implements CreationOperation<Tensor<V>> {

    private final Shape shape;
    private final V value;

    public SingleValueTensorCreationOperation(Shape shape, V value) {
        super();
        this.shape = shape;
        this.value = value;
    }

    @Override
    public Tensor<V> perform() {
        Builder<V> builder = ImmutableTensor.builder(shape.dimensionSet());
        for (Position position : shape.positionSet()) {
            builder.at(position).put(value);
        }
        return builder.build();
    }

}
