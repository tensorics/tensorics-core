package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Function;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.resample.impl.LinearInterpolationResampler;
import org.tensorics.core.tensor.resample.impl.RepeatingResampler;

public class OngoingFieldAwareResamplingStart<V> {

    private final Tensor<V> tensor;
    private final ExtendedField<V> field;

    public OngoingFieldAwareResamplingStart(Tensor<V> tensor, ExtendedField<V> environment) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
        this.field = requireNonNull(environment, "field must not be null");
    }

    public <C> OngoingFieldAwareResampling<V> linear(Class<C> dimension, Function<C, V> xConversion) {
        return OngoingFieldAwareResampling.of(tensor, dimension, new LinearInterpolationResampler<>(field, xConversion),
                field);
    }

    public <T> OngoingFieldAwareResampling<V> repeat(Class<T> dimension, Comparator<T> dimensionComparator) {
        return OngoingFieldAwareResampling.of(tensor, dimension, new RepeatingResampler<>(dimensionComparator), field);
    }

    public <T extends Comparable<T>> OngoingFieldAwareResampling<V> repeat(Class<T> dimension) {
        return repeat(dimension, Comparator.naturalOrder());
    }

}
