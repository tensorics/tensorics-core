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

package org.tensorics.core.tensor.lang;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.tensorics.core.tensor.Coordinates;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Positions;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorInternals;
import org.tensorics.core.tensor.stream.TensorStreams;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Part of the tensoric fluent API which provides methods to describe misc manipulations on a given tensor.
 *
 * @author kfuchsbe
 * @param <V> the type of the values of the tensor
 */
public class OngoingTensorManipulation<V> {

    private final Tensor<V> tensor;

    OngoingTensorManipulation(Tensor<V> tensor) {
        super();
        this.tensor = tensor;
    }

    /**
     * Extracts from the tensor only those elements where the values in the given mask is {@code true}. The resulting
     * tensors will then have the same dimensionality as the original tensor, but will only have that many elements as
     * there are {@code true} elements in the mask tensor.
     *
     * @param mask the mask which determines which elements shall be present in the new tensor.
     * @return A tensor which will contain only those elements which have {@code true} flags in the mask
     */
    public Tensor<V> extractWhereTrue(Tensor<Boolean> mask) {
        Builder<V> tensorBuilder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        for (java.util.Map.Entry<Position, V> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            if (mask.get(entry.getKey()).booleanValue()) {
                tensorBuilder.put(entry.getKey(), entry.getValue());
            }
        }
        return tensorBuilder.build();
    }

    /**
     * Retrieves all the unique coordinates of the given type.
     *
     * @param coordinateType the type of the coordinate to extract
     * @return a set of extracted coordinates
     */
    public <C1> Set<C1> extractCoordinatesOfType(Class<C1> coordinateType) {
        Set<C1> toReturn = new HashSet<>();
        for (Position position : tensor.shape().positionSet()) {
            toReturn.add(Coordinates.firstCoordinateOfTyp(position.coordinates(), coordinateType));
        }
        return toReturn;
    }

    public V get(Position position) {
        return tensor.get(position);
    }

    public V get(Object... coordinates) {
        return tensor.get(coordinates);
    }

    /**
     * Returns an optional containing the value at the given position in the tensor, if the tensor contains the
     * position, or an empty optional otherwise. However, if the dimension of the position are incompatible with the
     * tensor dimensions, then an {@link IllegalArgumentException} is thrown.
     * 
     * @param position the position for which the optional value shall be queried.
     * @return an optional containing the value, if it is contained in the tensor and the dimensions are correct.
     * @throws IllegalArgumentException if the dimensions of the tensor and the position do not match.
     */
    public Optional<V> optional(Position position) {
        if (tensor.contains(position)) {
            return Optional.of(get(position));
        }
        Set<Class<?>> tensorDimensions = tensor.shape().dimensionSet();
        if (Positions.areDimensionsConsistentWithCoordinates(tensorDimensions, position)) {
            return Optional.empty();
        }
        String message = "The dimensions of the tensor (" + tensorDimensions
                + ") do not match the dimensions of the requested position (" + position + ").";
        throw new IllegalArgumentException(message);
    }

    public Optional<V> optional(Object... coordinates) {
        return optional(Position.of(coordinates));
    }

    public <C> List<V> list(List<C> listCoordinateValues, Position otherCoordinates) {
        return listCoordinateValues.stream().map(Position::of)//
                .map(p -> Positions.union(otherCoordinates, p)) //
                .map(p -> get(p))//
                .collect(toImmutableList());
    }

    public <C> List<V> list(List<C> listCoordinateValues, Object... otherCoordinates) {
        return list(listCoordinateValues, Position.of(otherCoordinates));
    }

    public <C> Map<C, V> map(Class<C> mapKeyType, Position otherCoordinates) {
        Tensor<V> remaining = extract(otherCoordinates);
        return TensorStreams.tensorEntryStream(remaining)
                .collect(ImmutableMap.toImmutableMap(e -> e.getKey().coordinateFor(mapKeyType), e -> e.getValue())); //
    }

    public <C> Map<C, V> map(Class<C> mapKeyType, Object... otherCoordinates) {
        return map(mapKeyType, Position.of(otherCoordinates));
    }

    public Tensor<V> extract(Position position) {
        return extractTensor(position.coordinates());
    }

    public Tensor<V> extract(Object... coordinates) {
        return extractTensor(Arrays.asList(coordinates));
    }

    /**
     * @deprecated use rather {@link #optional(Object...)}.orElse(...);
     */
    @Deprecated
    public OngoingEitherGet<V> either(V defaultValue) {
        return new OngoingEitherGet<>(tensor, defaultValue);
    }

    private Tensor<V> extractTensor(Collection<?> coordinates) {
        checkArgument(coordinates != null, "Argument 'coordinates' must not be null!");
        for (Object coordinate : coordinates) {
            checkArgument(coordinate != null, "given coordinate must not be null!");
        }
        Tensor<V> slice = tensor;
        for (Object coordinate : coordinates) {
            slice = slice(slice, coordinate);
        }
        return slice;
    }

    private static final <C, E> Tensor<E> slice(Tensor<E> tensor, C coordinate) {
        checkArgument(coordinate != null, "Argument '" + "coordinate" + "' must not be null!");
        checkArgument(!(coordinate instanceof Position), "It is not allowed that a coordinate is of type position! "
                + "Most probably this is a programming mistake ;-)");

        /*
         * TODO: write a nice test for this and probably have a method in shape to map dimensions
         */
        @SuppressWarnings("unchecked")
        Class<C> dimension = (Class<C>) coordinate.getClass();
        Class<? super C> correctDimension = Coordinates.mapToAnEntry(dimension, tensor.shape().dimensionSet());

        return TensorStructurals.from(tensor).reduce(correctDimension).bySlicingAt(coordinate);
    }

    public <C> OngoingDimensionReduction<C, V> reduce(Class<C> dimension) {
        return new OngoingDimensionReduction<>(tensor, dimension);
    }

}
