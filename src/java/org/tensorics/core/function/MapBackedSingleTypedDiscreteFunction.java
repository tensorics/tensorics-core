/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tensorics.incubate.function.IllegalDiscreteFunctionUsageException;

/**
 * This implementation of {@link SingleTypedDiscreteFunction} only provides values at discrete points of X argument. If
 * a y-value is requested for unavailable x-value an {@link IllegalDiscreteFunctionUsageException} is thrown.
 * 
 * @author caguiler
 * @param <X> type for arguments and values of the function
 */
public class MapBackedSingleTypedDiscreteFunction<V> implements SingleTypedDiscreteFunction<V> {

    private final DiscreteFunction<V, V> backingFunction;

    public MapBackedSingleTypedDiscreteFunction(DiscreteFunction<V, V> backingFunction) {
        this.backingFunction = backingFunction;
    }

    @Override
    public V apply(V input) {
        return backingFunction.apply(input);
    }

    @Override
    public Set<V> definedXValues() {
        return backingFunction.definedXValues();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((backingFunction == null) ? 0 : backingFunction.hashCode());
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
        if (!(obj instanceof MapBackedSingleTypedDiscreteFunction)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        MapBackedSingleTypedDiscreteFunction other = (MapBackedSingleTypedDiscreteFunction) obj;
        if (backingFunction == null) {
            if (other.backingFunction != null) {
                return false;
            }
        } else if (!backingFunction.equals(other.backingFunction)) {
            return false;
        }
        return true;
    }

    public static final class Builder<V> implements DiscreteFunctionBuilder<V, V> {

        private final MapBackedDiscreteFunction.Builder<V, V> delegate = MapBackedDiscreteFunction.builder();

        @Override
        public DiscreteFunctionBuilder<V, V> put(V key, V value) {
            delegate.put(key, value);
            return this;
        }

        @Override
        public DiscreteFunctionBuilder<V, V> put(Entry<? extends V, ? extends V> entry) {
            delegate.put(entry);
            return this;
        }

        @Override
        public DiscreteFunctionBuilder<V, V> putAll(Map<? extends V, ? extends V> values) {
            delegate.putAll(values);
            return this;
        }

        @Override
        public MapBackedSingleTypedDiscreteFunction<V> build() {
            return new MapBackedSingleTypedDiscreteFunction<>(delegate.build());
        }

    }

    public static final <V> Builder<V> builder() {
        return new Builder<>();
    }

}
