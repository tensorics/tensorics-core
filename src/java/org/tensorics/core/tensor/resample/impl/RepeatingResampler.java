package org.tensorics.core.tensor.resample.impl;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;

import org.tensorics.core.tensor.resample.SingleDimensionResampler;

import com.google.common.collect.ImmutableSortedSet;

/**
 * Resampler that repeats values in one dimension. It requires a comparator for coordinates of the concerned dimension.
 * If possible, values are repeated from the previous point. If the requested point is before the first point, then the
 * value is 'repeated' from the next point.
 * 
 * @author kfuchsbe
 * @param <C> the type of the coordinate in whos direction to resample
 * @param <V> the type of the tensor values
 */
public class RepeatingResampler<C, V> implements SingleDimensionResampler<C, V> {

    private final Comparator<C> comparator;

    /**
     * Constructs a new resampler, given a comparator for the relevant coordinates
     * 
     * @param comparator the comparator for the coordinates in question
     */
    public RepeatingResampler(Comparator<C> comparator) {
        this.comparator = requireNonNull(comparator, "comparator must not be null");
    }

    @Override
    public V resample(Set<C> coordinates, Function<C, V> valuesCallback, C coordinate) {
        if (coordinates.isEmpty()) {
            throw new NoSuchElementException(
                    "No supporting points available for repeating at coordinat '" + coordinate + "'.");
        }
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

        /* Should never happen */
        throw new NoSuchElementException("Tensor cannot be resampled at coordinate '" + coordinate + "'.");
    }

    @Override
    public boolean canResample(Set<C> coordinates, C coordinate) {
        return !coordinates.isEmpty();
    }
}
