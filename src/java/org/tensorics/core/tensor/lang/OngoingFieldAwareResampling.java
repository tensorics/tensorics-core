package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;
import org.tensorics.core.tensor.resample.MultiDimensionalResampling;
import org.tensorics.core.tensor.resample.SingleDimensionRepeatingResampler;
import org.tensorics.core.tensor.resample.SingleDimensionResampler;

public class OngoingFieldAwareResampling<V> {

    private final Tensor<V> tensor;
    private final MultiDimensionalResampling<V> ordering;
    private final Environment<V> environment;

    private OngoingFieldAwareResampling(Tensor<V> tensor, MultiDimensionalResampling<V> resampling,
            Environment<V> environment) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
        this.ordering = requireNonNull(resampling, "resampling must not be null");
        this.environment = requireNonNull(environment, "environment must not be null");
    }

    public static final <C, V> OngoingFieldAwareResampling<V> of(Tensor<V> tensor, Class<C> dimension,
            SingleDimensionResampler<C, V> resampler, Environment<V> environment) {
        return new OngoingFieldAwareResampling<>(tensor, MultiDimensionalResampling.resample(dimension, resampler),
                environment);
    }

    public final <T extends Comparable<T>> OngoingFieldAwareResampling<V> repeat(Class<T> dimension) {
        return repeating(dimension, Comparator.naturalOrder());
    }

    public final <T> OngoingFieldAwareResampling<V> repeating(Class<T> dimension, Comparator<T> dimensionComparator) {
        return then(dimension, new SingleDimensionRepeatingResampler<>(dimensionComparator));
    }

    public <C> OngoingFieldAwareResampling<V> then(Class<C> dimension, SingleDimensionResampler<C, V> resampler) {
        return new OngoingFieldAwareResampling<>(tensor, ordering.then(dimension, resampler), environment);
    }

    /**
     * NOOP, to be used for more fluent clauses.
     * 
     * @return this instance
     */
    public OngoingFieldAwareResampling<V> then() {
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
