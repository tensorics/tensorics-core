/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.tensorics.core.commons.util.ValuePair;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.structures.grouplike.Group;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

/**
 * Contains utility methods for operations.
 * 
 * @author kfuchsbe
 */
public final class Operations {

    private Operations() {
        /* Only static methods */
    }

    public static <T> BinaryOperation<T> inverseBinaryFor(Group<T> group) {
        return new InverseOperationView<T>(group);
    }

    public static <V> List<V> performOnAll(Iterable<ValuePair<V>> valuePairs, BinaryOperation<V> operation) {
        ImmutableList.Builder<V> builder = ImmutableList.builder();
        for (ValuePair<V> valuePair : valuePairs) {
            builder.add(operation.perform(valuePair.left(), valuePair.right()));
        }
        return builder.build();
    }

    public static <K, V> ListMultimap<K, V> mapAll(Multimap<K, ValuePair<V>> valuePairs,
            BinaryOperation<V> operation) {
        ImmutableListMultimap.Builder<K, V> builder = ImmutableListMultimap.builder();
        for (Entry<K, Collection<ValuePair<V>>> entry : valuePairs.asMap().entrySet()) {
            builder.putAll(entry.getKey(), performOnAll(entry.getValue(), operation));
        }
        return builder.build();
    }

    private static final class InverseOperationView<T> implements BinaryOperation<T> {
        private final Group<T> group;

        InverseOperationView(Group<T> group) {
            super();
            this.group = group;
        }

        @Override
        public T perform(T left, T right) {
            return group.operation().perform(left, group.inversion().perform(right));
        }

    }

}
