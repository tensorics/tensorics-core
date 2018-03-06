package org.tensorics.core.tensor.resample;

import com.google.common.collect.ImmutableSortedSet;
import org.tensorics.core.tensor.Position;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.function.Function;

public class SingleDimensionRepeatingResamplingStrategy<C, V> implements SingleDimensionResamplingStrategy<C, V> {

    private Class<C> dimension;
    private Comparator<C> comparator;

    @Override
    public V resample(Collection<C> coordinates, Function<C, V> valuesCallback, C coordinate) {
        if (coordinates.contains(coordinate)) {
            return valuesCallback.apply(coordinate);
        }

        SortedSet<C> orderedCoordinates = ImmutableSortedSet.copyOf(comparator, coordinates);

        SortedSet<C> before = orderedCoordinates.headSet(coordinate);
        if (!before.isEmpty()) {
            return valuesCallback.apply(before.last());
        }

        SortedSet<C> after = orderedCoordinates.tailSet(coordinate);
        if (!after.isEmpty()) {
            return valuesCallback.apply(after.first());
        }

        throw new NoSuchElementException("Tensor cannot be resampled at '" + coordinate + "'");
    }
}


