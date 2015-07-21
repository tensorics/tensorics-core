/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

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
        for (java.util.Map.Entry<Position, V> entry : tensor.asMap().entrySet()) {
            if (mask.get(entry.getKey()).booleanValue()) {
                tensorBuilder.at(entry.getKey()).put(entry.getValue());
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
            toReturn.add(position.getCoordinates().getInstance(coordinateType));
        }
        return toReturn;
    }

    public V get(Position position) {
        return tensor.get(position);
    }

    public V get(Object... coordinates) {
        return tensor.get(coordinates);
    }

    public Tensor<V> extract(Position position) {
        return extractTensor(position.coordinates());
    }

    public Tensor<V> extract(Object... coordinates) {
        return extractTensor(Arrays.asList(coordinates));
    }

    private Tensor<V> extractTensor(Collection<?> coordinates) {
        checkArgument(coordinates != null, "Argument '" + "coordinate" + "' must not be null!");
        checkArgument(coordinates.size() > 0, "Coordinates must have at least one element.");
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
        @SuppressWarnings("unchecked")
        Class<C> dimension = (Class<C>) coordinate.getClass();
        return TensorStructurals.from(tensor).reduce(dimension).bySlicingAt(coordinate);
    }

    public <C> OngoingDimensionReduction<C, V> reduce(Class<C> dimension) {
        return new OngoingDimensionReduction<>(tensor, dimension);
    }

}
