package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.coordinates.PositionOrdering;

public class OngoingResampling<V> {

    private final Tensor<V> tensor;

    public OngoingResampling(Tensor<V> tensor) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
    }

    public <T> OngoingRepeatingResampling<V> byRepeating(Class<T> dimension, Comparator<T> dimensionComparator) {
        return new OngoingRepeatingResampling<>(tensor, PositionOrdering.of(dimension, dimensionComparator));
    }

    public <T extends Comparable<T>> OngoingRepeatingResampling<V> byRepeatingAlong(Class<T> dimension) {
        return new OngoingRepeatingResampling<>(tensor, PositionOrdering.of(dimension));
    }

}
