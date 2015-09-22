package org.tensorics.core.tensor.lang;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map.Entry;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.Range;

/**
 * Part of a fluent clause to filter elements of a tensor.
 * 
 * @author kfuchsbe
 * @param <E> the type of the tensor elements
 */
public class OngoingTensorFiltering<E> {

    private final Tensor<E> tensor;

    public OngoingTensorFiltering(Tensor<E> tensor) {
        super();
        this.tensor = tensor;
    }

    public <C extends Comparable<C>> Tensor<E> by(Class<C> coordinateClass, Range<C> coordinateRange) {
        checkNotNull(coordinateClass, "coordinateClass must not be null");
        checkNotNull(coordinateRange, "coordinateRange must not be null");

        ImmutableTensor.Builder<E> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        builder.setTensorContext(tensor.context());
        for (Entry<Position, E> entry : tensor.asMap().entrySet()) {
            if (coordinateRange.contains(entry.getKey().coordinateFor(coordinateClass))) {
                builder.putAt(entry.getValue(), entry.getKey());
            }
        }
        return builder.build();
    }
}
