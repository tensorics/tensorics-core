package org.tensorics.core.tensor.resample;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.function.Function;

import com.google.common.collect.ImmutableSortedSet;

public class SingleDimensionRepeatingResampler<C, V> implements SingleDimensionResampler<C, V> {

    private final Comparator<C> comparator;

    public SingleDimensionRepeatingResampler(Comparator<C> comparator) {
        this.comparator = requireNonNull(comparator);
    }

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

        throw new NoSuchElementException("Tensor cannot be resampled at coordinate '" + coordinate + "'");
    }

    @Override
    public boolean canResample(Collection<C> coordinates, C coordinate) {
        return !coordinates.isEmpty();
    }
}
