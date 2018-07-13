package org.tensorics.core.tensor.resample;

import java.util.Collection;
import java.util.function.Function;

/**
 * Defines how to resample in one dimension of a tensor.
 */
public interface SingleDimensionResampler<C, V> {

    V resample(Collection<C> coordinates, Function<C, V> values, C coordinate);

    boolean canResample(Collection<C> coordinates, C coordinate);
}
