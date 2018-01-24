package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.coordinates.PositionOrdering;
import org.tensorics.core.tensor.resample.RepeatingResamplingStrategy;

public class OngoingRepeatingResampling<V> {

    private final Tensor<V> tensor;
    private final PositionOrdering ordering;

    OngoingRepeatingResampling(Tensor<V> tensor, PositionOrdering ordering) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
        this.ordering = requireNonNull(ordering, "ordering must not be null");
    }

    public final <T> OngoingRepeatingResampling<V> thenAlong(Class<T> dimension, Comparator<T> dimensionComparator) {
        return along(ordering.then(dimension, dimensionComparator));
    }

    public final <T extends Comparable<T>> OngoingRepeatingResampling<V> thenAlong(Class<T> dimension) {
        return along(ordering.then(dimension));
    }

    private OngoingRepeatingResampling<V> along(PositionOrdering then) {
        return new OngoingRepeatingResampling<>(tensor, then);
    }

    public final V get(Position position) {
        return RepeatingResamplingStrategy.of(ordering).resample(tensor, position);
    }

    public final V get(Object... coordinates) {
        return get(Position.of(coordinates));
    }

    public final V get(Iterable<?> coordinates) {
        return get(Position.of(coordinates));
    }

}
