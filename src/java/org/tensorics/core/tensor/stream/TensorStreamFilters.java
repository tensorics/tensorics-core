/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.stream;

import java.util.Map;
import java.util.function.Predicate;

import org.tensorics.core.tensor.Position;

public final class TensorStreamFilters {

    private TensorStreamFilters() {
        /* static only */
    }

    public static <T, C> Predicate<Map.Entry<Position, T>> byCoordinateOfType(Class<C> dimension,
            Predicate<C> positionPredicate) {
        return entry -> positionPredicate.test(entry.getKey().coordinateFor(dimension));
    }

    public static <T> Predicate<Map.Entry<Position, T>> byPosition(Predicate<Position> positionPredicate) {
        return entry -> positionPredicate.test(entry.getKey());
    }

    public static <T> Predicate<Map.Entry<Position, T>> byValue(Predicate<T> valuePredicate) {
        return entry -> valuePredicate.test(entry.getValue());
    }
}
