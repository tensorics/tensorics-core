package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.resample.impl.RepeatingResampler;

public class OngoingResamplingStart<V> {

    private final Tensor<V> tensor;

    public OngoingResamplingStart(Tensor<V> tensor) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
    }

    public <T> OngoingResampling<V> repeat(Class<T> dimension, Comparator<T> dimensionComparator) {
        return OngoingResampling.of(tensor, dimension, new RepeatingResampler<>(dimensionComparator));
    }

    public <T extends Comparable<T>> OngoingResampling<V> repeat(Class<T> dimension) {
        return repeat(dimension, Comparator.naturalOrder());
    }

}
