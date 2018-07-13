package org.tensorics.core.tensor.coordinates;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.tensorics.core.tensor.Coordinates;
import org.tensorics.core.tensor.Position;

import com.google.common.collect.ImmutableList;

/**
 * An instance of this class defines both, the ordering of different dimensions amongst each other and the order of
 * coordinates within each dimension.
 * 
 * @author kaifox
 */
public class PositionOrdering {

    /* This on purpose has to be a linked hashmap, because the insertion order has to be preserved. */
    private final LinkedHashMap<Class<?>, Comparator<?>> dimensionComparators;

    private PositionOrdering(LinkedHashMap<Class<?>, Comparator<?>> dimensionComparators) {
        /* This should never happen, because of the factory methods. Still we keep the check for the moment. */
        if (dimensionComparators.isEmpty()) {
            throw new IllegalArgumentException("No dimension is defined for ordering.");
        }
        /* A new linked hash set is created in the factory methods. Therefore, no additional copying required here. */
        this.dimensionComparators = dimensionComparators;
    }

    public static final <T> PositionOrdering of(Class<T> dimension, Comparator<T> dimensionComparator) {
        requireNonNull(dimension, "dimension must not be null.");
        requireNonNull(dimensionComparator, "dimensionComparator must not be null.");

        LinkedHashMap<Class<?>, Comparator<?>> comparators = new LinkedHashMap<>();
        comparators.put(dimension, dimensionComparator);
        return new PositionOrdering(comparators);
    }

    public static final <T extends Comparable<T>> PositionOrdering of(Class<T> dimension) {
        requireNonNull(dimension, "dimension must not be null.");
        return of(dimension, Comparator.naturalOrder());
    }

    public final <T> PositionOrdering then(Class<T> dimension, Comparator<T> dimensionComparator) {
        requireNonNull(dimension, "dimension must not be null.");
        requireNonNull(dimensionComparator, "dimensionComparator must not be null.");

        LinkedHashMap<Class<?>, Comparator<?>> comparators = new LinkedHashMap<>(this.dimensionComparators);
        if (comparators.containsKey(dimension)) {
            throw new IllegalArgumentException("You are trying to add the dimension '" + dimension
                    + "' twice to the comparable dimensions. This is not allowed.");
        }

        comparators.put(dimension, dimensionComparator);
        Coordinates.checkClassesRelations(comparators.keySet());
        return new PositionOrdering(comparators);
    }

    public final <T extends Comparable<T>> PositionOrdering then(Class<T> dimension) {
        requireNonNull(dimension, "dimension must not be null.");
        return then(dimension, Comparator.naturalOrder());
    }

    public List<Class<?>> dimensions() {
        return ImmutableList.copyOf(this.dimensionComparators.keySet());
    }

    public Comparator<Position> positionComparator() {
        return this.dimensionComparators.keySet().stream().map(d -> positionComparator(d))
                .reduce((l, r) -> l.thenComparing(r)).get();
    }

    public <T> Comparator<T> comparatorFor(Class<T> dimension) {
        /* This cast is safe, as we ensure the correct type when putting the comparator into the map */
        @SuppressWarnings("unchecked")
        Comparator<T> comparator = (Comparator<T>) this.dimensionComparators.get(dimension);
        return comparator;
    }

    private <T> Comparator<Position> positionComparator(Class<T> dimension) {
        return Comparator.comparing(p -> p.coordinateFor(dimension), comparatorFor(dimension));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dimensionComparators == null) ? 0 : dimensionComparators.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PositionOrdering other = (PositionOrdering) obj;
        if (dimensionComparators == null) {
            if (other.dimensionComparators != null) {
                return false;
            }
        } else if (!dimensionComparators.equals(other.dimensionComparators)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PositionOrdering [dimensionComparators=" + dimensionComparators + "]";
    }

}
