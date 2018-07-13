package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.resample.impl.RepeatingResampler;

public class OngoingFieldAwareResamplingStart<V> {

    private final Tensor<V> tensor;
    private final Environment<V> environment;

    public OngoingFieldAwareResamplingStart(Tensor<V> tensor, Environment<V> environment) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
        this.environment = requireNonNull(environment, "environment must not be null");
    }

    public <T> OngoingFieldAwareResampling<V> repeat(Class<T> dimension, Comparator<T> dimensionComparator) {
        return OngoingFieldAwareResampling.of(tensor, dimension,
                new RepeatingResampler<>(dimensionComparator), environment);
    }

    public <T extends Comparable<T>> OngoingFieldAwareResampling<V> repeat(Class<T> dimension) {
        return repeat(dimension, Comparator.naturalOrder());
    }

}
