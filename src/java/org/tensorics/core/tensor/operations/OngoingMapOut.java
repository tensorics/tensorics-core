/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.operations;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

/**
 * Provides methods to describe how to produce a map out of a tensor.
 * <p>
 * This is part of a tensoric-internal fluent API
 * 
 * @author kfuchsbe
 * @param <V> the type of values of the tensor
 */
public final class OngoingMapOut<V> {
    private final Tensor<V> tensor;

    public OngoingMapOut(Tensor<V> tensor) {
        this.tensor = tensor;
    }

    public <C1> Tensor<Map<C1, V>> inDirectionOf(Class<? extends C1> dimension) {
        Builder<Map<C1, V>> tensorBuilder = ImmutableTensor.builder(OngoingMapOut.dimensionsExcept(tensor.shape()
                .dimensionSet(), dimension));
        Multimap<Set<?>, Tensor.Entry<V>> fullEntries = groupBy(tensor.entrySet(), dimension);
        for (Set<?> key : fullEntries.keySet()) {
            Map<C1, V> values = mapByDimension(fullEntries.get(key), dimension);
            tensorBuilder.at(Position.of(key)).put(values);
        }
        return tensorBuilder.build();
    }

    private static <C, C1 extends C> Set<?> coordinatesExcept(Collection<?> coordinates, Class<C1> dimension) {
        ImmutableSet.Builder<Object> builder = ImmutableSet.builder();
        for (Object coordinate : coordinates) {
            if (!dimension.isInstance(coordinate)) {
                builder.add(coordinate);
            }
        }
        return builder.build();
    }

    private static <C, C1 extends C> Set<Class<?>> dimensionsExcept(Set<Class<?>> dimensions, Class<C1> dimension) {
        Set<Class<?>> toReturn = new HashSet<>();
        for (Class<?> oneDimension : dimensions) {
            if (!oneDimension.equals(dimension)) {
                toReturn.add(oneDimension);
            }
        }
        return toReturn;
    }

    private static <C, C1 extends C, T> Map<C1, T> mapByDimension(Collection<Tensor.Entry<T>> entries,
            Class<? extends C1> dimension) {
        ImmutableMap.Builder<C1, T> valuesBuilder = ImmutableMap.builder();
        for (Tensor.Entry<T> entry : entries) {
            C1 coordinateFor = entry.getPosition().coordinateFor(dimension);
            if (coordinateFor == null) {
                throw new IllegalStateException("Cannot operate with [" + dimension + "] while having only "
                        + entry.getPosition());
            }
            valuesBuilder.put(coordinateFor, entry.getValue());
        }
        return valuesBuilder.build();
    }

    private static <T, C, C1 extends C> Multimap<Set<?>, Tensor.Entry<T>> groupBy(Iterable<Tensor.Entry<T>> entries,
            Class<C1> dimension) {
        ImmutableMultimap.Builder<Set<?>, Tensor.Entry<T>> fullEntriesBuilder = ImmutableMultimap.builder();
        for (Tensor.Entry<T> entry : entries) {
            Collection<?> coordinates = entry.getPosition().coordinates();
            fullEntriesBuilder.put(OngoingMapOut.coordinatesExcept(coordinates, dimension), entry);
        }
        return fullEntriesBuilder.build();
    }
}