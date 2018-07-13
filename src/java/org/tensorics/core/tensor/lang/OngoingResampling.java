package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.resample.SingleDimensionRepeatingResampler;

public class OngoingResampling<V> {

    private final Tensor<V> tensor;

    public OngoingResampling(Tensor<V> tensor) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
    }

    public <T> OngoingRepeatingResampling<V> byRepeating(Class<T> dimension, Comparator<T> dimensionComparator) {
        return OngoingRepeatingResampling.of(tensor, dimension,
                new SingleDimensionRepeatingResampler<>(dimensionComparator));
    }

    public <T extends Comparable<T>> OngoingRepeatingResampling<V> byRepeating(Class<T> dimension) {
        return byRepeating(dimension, Comparator.naturalOrder());
    }

}
