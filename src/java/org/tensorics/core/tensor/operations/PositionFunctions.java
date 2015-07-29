package org.tensorics.core.tensor.operations;

import org.tensorics.core.tensor.Position;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

public final class PositionFunctions {

    private PositionFunctions() {

    }

    public static final <V> Function<Position, V> constant(final V value) {
        return new Function<Position, V>() {

            @Override
            public V apply(Position position) {
                return value;
            }
        };
    }

    public static final <V> Function<Position, V> forSupplier(final Supplier<V> supplier) {
        return new Function<Position, V>() {

            @Override
            public V apply(Position position) {
                return supplier.get();
            }
        };
    }
}
