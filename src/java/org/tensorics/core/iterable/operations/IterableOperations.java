/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;

/**
 * Provides static utility methods related to iterable operations
 * 
 * @author kfuchsbe
 */
public final class IterableOperations {

    /**
     * Private Constructor to avoid instantiation
     */
    private IterableOperations() {
        /* Only static methods */
    }

    public static <K, V> Map<K, V> reduce(Multimap<K, V> values, IterableOperation<V> operation) {
        return reduce(values.asMap(), operation);
    }

    /**
     * Reduces all the collection-values of the multimap by the use of the given operation and returns a map containing
     * the result of the respective operation.
     * 
     * @param values a map containg K->values, for which the values shall be treated by the operation
     * @param operation the operation which shall be applied to the collection-values
     * @return a mapt K->value, where value is the result of the operation
     */
    private static <K, V> Map<K, V> reduce(Map<K, Collection<V>> values, IterableOperation<V> operation) {
        ImmutableMap.Builder<K, V> resultBuilder = ImmutableMap.builder();
        for (Entry<K, Collection<V>> entry : values.entrySet()) {
            resultBuilder.put(entry.getKey(), operation.perform(entry.getValue()));
        }
        return resultBuilder.build();
    }

}
