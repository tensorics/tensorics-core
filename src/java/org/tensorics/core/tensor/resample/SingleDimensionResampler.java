package org.tensorics.core.tensor.resample;

import java.util.Set;
import java.util.function.Function;

/**
 * Defines how to resample in one dimension of a tensor.
 */
public interface SingleDimensionResampler<C, V> {

    V resample(Set<C> coordinates, Function<C, V> values, C coordinate);

    boolean canResample(Set<C> coordinates, C coordinate);
}
