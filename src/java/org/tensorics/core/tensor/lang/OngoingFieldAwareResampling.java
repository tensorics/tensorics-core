package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Function;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;
import org.tensorics.core.tensor.resample.MultiDimensionalResampling;
import org.tensorics.core.tensor.resample.SingleDimensionResampler;
import org.tensorics.core.tensor.resample.impl.LinearInterpolationResampler;
import org.tensorics.core.tensor.resample.impl.RepeatingResampler;

public class OngoingFieldAwareResampling<V> {

    private final Tensor<V> tensor;
    private final MultiDimensionalResampling<V> ordering;
    private final ExtendedField<V> field;

    private OngoingFieldAwareResampling(Tensor<V> tensor, MultiDimensionalResampling<V> resampling,
            ExtendedField<V> field) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
        this.ordering = requireNonNull(resampling, "resampling must not be null");
        this.field = requireNonNull(field, "field must not be null");
    }

    public static final <C, V> OngoingFieldAwareResampling<V> of(Tensor<V> tensor, Class<C> dimension,
            SingleDimensionResampler<C, V> resampler, ExtendedField<V> field) {
        return new OngoingFieldAwareResampling<>(tensor, MultiDimensionalResampling.resample(dimension, resampler),
                field);
    }

    public final <C extends Comparable<C>> OngoingFieldAwareResampling<V> repeat(Class<C> dimension) {
        return repeat(dimension, Comparator.naturalOrder());
    }

    public final <C> OngoingFieldAwareResampling<V> repeat(Class<C> dimension, Comparator<C> dimensionComparator) {
        return then(dimension, new RepeatingResampler<>(dimensionComparator));
    }

    public final <C> OngoingFieldAwareResampling<V> linear(Class<C> dimension, Function<C, V> xConversion) {
        return then(dimension, new LinearInterpolationResampler<>(field, xConversion));
    }

    public <C> OngoingFieldAwareResampling<V> then(Class<C> dimension, SingleDimensionResampler<C, V> resampler) {
        return new OngoingFieldAwareResampling<>(tensor, ordering.then(dimension, resampler), field);
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
