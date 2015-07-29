package org.tensorics.core.tensor.operations;

import org.tensorics.core.math.operations.CreationOperation;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Functions;

/**
 * Uses one value and a given shape to create a tensor, which has the same values at all positions.
 * 
 * @author kaifox
 * @param <V> the type of the elements of the tensor to be created
 */
public class SingleValueTensorCreationOperation<V> extends FunctionTensorCreationOperation<V> {

    public SingleValueTensorCreationOperation(Shape shape, final V value) {
        super(shape, PositionFunctions.constant(value));
    }

}
