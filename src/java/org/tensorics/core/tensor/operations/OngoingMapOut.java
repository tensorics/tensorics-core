// @formatter:off
/*******************************************************************************
*
* This file is part of tensorics.
* 
* Copyright (c) 2008-2011, CERN. All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
******************************************************************************/
// @formatter:on

package org.tensorics.core.tensor.operations;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
        Builder<Map<C1, V>> tensorBuilder = ImmutableTensor
                .builder(OngoingMapOut.dimensionsExcept(tensor.shape().dimensionSet(), dimension));

        tensorBuilder.context(tensor.context()); // XXX IS this correct?

        Multimap<Set<?>, Entry<Position, V>> fullEntries = groupBy(tensor.asMap().entrySet(), dimension);
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

    private static <C, C1 extends C, T> Map<C1, T> mapByDimension(Collection<Entry<Position, T>> entries,
            Class<? extends C1> dimension) {
        ImmutableMap.Builder<C1, T> valuesBuilder = ImmutableMap.builder();
        for (Entry<Position, T> entry : entries) {
            C1 coordinateFor = entry.getKey().coordinateFor(dimension);
            if (coordinateFor == null) {
                throw new IllegalArgumentException(
                        "Cannot operate with [" + dimension + "] while having only " + entry.getKey());
            }
            valuesBuilder.put(coordinateFor, entry.getValue());
        }
        return valuesBuilder.build();
    }

    private static <T, C, C1 extends C> Multimap<Set<?>, Entry<Position, T>> groupBy(
            Iterable<Entry<Position, T>> entries, Class<C1> dimension) {
        ImmutableMultimap.Builder<Set<?>, Entry<Position, T>> fullEntriesBuilder = ImmutableMultimap.builder();
        for (Entry<Position, T> entry : entries) {
            Collection<?> coordinates = entry.getKey().coordinates();
            fullEntriesBuilder.put(OngoingMapOut.coordinatesExcept(coordinates, dimension), entry);
        }
        return fullEntriesBuilder.build();
    }
}