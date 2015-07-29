package org.tensorics.core.tensor.operations;

import org.tensorics.core.math.operations.CreationOperation;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Function;

/**
 * Uses the given function from a position to the tensor to create the values of the tensor.
 * 
 * @author kaifox
 * @param <V> the type of the elements of the tensor to be created
 */
public class FunctionTensorCreationOperation<V> implements CreationOperation<Tensor<V>> {

    private final Shape shape;
    private final Function<Position, V> function;

    public FunctionTensorCreationOperation(Shape shape, Function<Position, V> function) {
        super();
        this.shape = shape;
        this.function = function;
    }

    @Override
    public Tensor<V> perform() {
        Builder<V> builder = ImmutableTensor.builder(shape.dimensionSet());
        for (Position position : shape.positionSet()) {
            builder.at(position).put(function.apply(position));
        }
        return builder.build();
    }

}
