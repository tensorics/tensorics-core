package org.tensorics.core.tensor.resample;

import java.util.Collection;
import java.util.SortedMap;
import java.util.function.Function;

/**
 * Defines how to resample in one dimension of a tensor.
 */
public interface SingleDimensionResamplingStrategy<C, V> {

    V resample(Collection<C> coordinates, Function<C, V> values, C coordinate);

}
