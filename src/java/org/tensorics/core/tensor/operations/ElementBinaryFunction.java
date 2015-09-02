package org.tensorics.core.tensor.operations;

import java.util.Collections;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Context;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorPair;
import org.tensorics.core.tensor.options.ContextPropagationStrategy;
import org.tensorics.core.tensor.options.BroadcastingStrategy;
import org.tensorics.core.tensor.options.ShapingStrategy;

public class ElementBinaryFunction<V, R> implements BinaryFunction<Tensor<V>, Tensor<R>> {

    protected final BinaryFunction<V, R> operation;
    protected final OptionRegistry<ManipulationOption> optionRegistry;

    public ElementBinaryFunction(BinaryFunction<V, R> operation, OptionRegistry<ManipulationOption> optionRegistry) {
        super();
        this.operation = operation;
        this.optionRegistry = optionRegistry;
    }

    @Override
    public Tensor<R> perform(Tensor<V> left, Tensor<V> right) {
        TensorPair<V> broadcastedPair = broadcast(left, right);
        Shape resultingShape = shape(broadcastedPair);
        Context resultingContext = contextLeftRight(left, right);
        return performOperation(broadcastedPair.left(), broadcastedPair.right(), resultingShape, resultingContext);
    }

    private Context contextLeftRight(Tensor<V> left, Tensor<V> right) {
        ContextPropagationStrategy strategy = optionRegistry.get(ContextPropagationStrategy.class);
        return strategy.contextForLeftRight(left.context(), right.context());
    }

    private Tensor<R> performOperation(Tensor<V> left, Tensor<V> right, Shape resultingShape, Context resultingContext) {
        Builder<R> tensorBuilder = ImmutableTensor.builder(resultingShape.dimensionSet());
        for (Position position : resultingShape.positionSet()) {
            tensorBuilder.at(position).put(operation.perform(left.get(position), right.get(position)));
        }
        tensorBuilder.setTensorContext(resultingContext);
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