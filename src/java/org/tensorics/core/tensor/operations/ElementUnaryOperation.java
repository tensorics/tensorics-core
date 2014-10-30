package org.tensorics.core.tensor.operations;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * Operates on one tensor and produces a new tensor of the same shape by applying a unary operation on each value of the
 * tensor. Thus it is uniquely defined by the operation on the elements.
 * 
 * @author kfuchsbe
 * @param <V> the type of the value of the tensor
 */
public class ElementUnaryOperation<V> implements UnaryOperation<Tensor<V>> {

    private final UnaryOperation<V> elementOperation;

    public ElementUnaryOperation(UnaryOperation<V> elementOperation) {
        super();
        this.elementOperation = elementOperation;
    }

    @Override
    public Tensor<V> perform(Tensor<V> tensor) {
        Shape shape = tensor.shape();
        Builder<V> builder = Tensorics.builder(shape.dimensionSet());
        for (Position position : shape.positionSet()) {
            builder.at(position).put(elementOperation.perform(tensor.get(position)));
        }
        return builder.build();
    }

}
