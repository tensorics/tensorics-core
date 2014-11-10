/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.operations;

import java.util.Collections;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorPair;
import org.tensorics.core.tensor.options.BroadcastingStrategy;
import org.tensorics.core.tensor.options.ShapingStrategy;

/**
 * A binary operations on tensors which can be decoupled into simple operations on an element of one tensor on the
 * corresponding element on the second tensor (same position). Because of this simple relation, the operation is fully
 * defined by a binary operation on individual tensor values.
 * <p>
 * To decide on the resulting shape of the tensor, two strategies are used here:
 * <ul>
 * <li> {@link BroadcastingStrategy}
 * <li> {@link ShapingStrategy}
 * </ul>
 * 
 * @author kfuchsbe
 * @param <V> The type of the tensor values
 */
public class ElementBinaryOperation<V> implements BinaryOperation<Tensor<V>> {
    private final BinaryOperation<V> operation;
    private final OptionRegistry<ManipulationOption> optionRegistry;

    public ElementBinaryOperation(BinaryOperation<V> operation, OptionRegistry<ManipulationOption> optionRegistry) {
        super();
        this.operation = operation;
        this.optionRegistry = optionRegistry;
    }

    @Override
    public Tensor<V> perform(Tensor<V> left, Tensor<V> right) {
        TensorPair<V> broadcastedPair = broadcast(left, right);
        Shape resultingShape = shape(broadcastedPair);
        return performOperation(broadcastedPair.left(), broadcastedPair.right(), resultingShape);
    }

    private Tensor<V> performOperation(Tensor<V> left, Tensor<V> right, Shape resultingShape) {
        Builder<V> tensorBuilder = ImmutableTensor.builder(resultingShape.dimensionSet());
        for (Position position : resultingShape.positionSet()) {
            tensorBuilder.at(position).put(operation.perform(left.get(position), right.get(position)));
        }
        return tensorBuilder.build();
    }

    private Shape shape(TensorPair<V> broadcasted) {
        ShapingStrategy strategy = optionRegistry.get(ShapingStrategy.class);
        return strategy.shapeLeftRight(broadcasted.left(), broadcasted.right());
    }

    private TensorPair<V> broadcast(Tensor<V> left, Tensor<V> right) {
        BroadcastingStrategy broadcastingStrategy = optionRegistry.get(BroadcastingStrategy.class);
        return broadcastingStrategy.broadcast(left, right, Collections.<Class<?>> emptySet());
    }

}
