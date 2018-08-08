package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;
import org.tensorics.core.tensor.resample.MultiDimensionalResampling;
import org.tensorics.core.tensor.resample.SingleDimensionResampler;
import org.tensorics.core.tensor.resample.impl.RepeatingResampler;

public class OngoingResampling<V> {

    private final Tensor<V> tensor;
    private final MultiDimensionalResampling<V> ordering;

    private OngoingResampling(Tensor<V> tensor, MultiDimensionalResampling<V> resampling) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
        this.ordering = requireNonNull(resampling, "resampling must not be null");
    }

    public static final <C, V> OngoingResampling<V> of(Tensor<V> tensor, Class<C> dimension,
            SingleDimensionResampler<C, V> resampler) {
        return new OngoingResampling<>(tensor, MultiDimensionalResampling.resample(dimension, resampler));
    }

    public final <T extends Comparable<T>> OngoingResampling<V> repeat(Class<T> dimension) {
        return repeating(dimension, Comparator.naturalOrder());
    }

    public final <T> OngoingResampling<V> repeating(Class<T> dimension, Comparator<T> dimensionComparator) {
        return then(dimension, new RepeatingResampler<>(dimensionComparator));
    }

    public <C> OngoingResampling<V> then(Class<C> dimension, SingleDimensionResampler<C, V> resampler) {
        return new OngoingResampling<>(tensor, ordering.then(dimension, resampler));
    }

    /**
     * NOOP, to be used for more fluent clauses.
     * 
     * @return this instance
     */
    public OngoingResampling<V> then() {
        return this;
    }

    public Tensoric<V> toTensoric() {
        return ordering.resample(tensor);
    }

    public final V get(Position position) {
        return toTensoric().get(position);
    }

    public final V get(Object... coordinates) {
        return get(Position.of(coordinates));
    }

    public final V get(Iterable<?> coordinates) {
        return get(Position.of(coordinates));
    }

}
