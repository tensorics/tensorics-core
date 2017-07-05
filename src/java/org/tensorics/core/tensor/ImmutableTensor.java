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

package org.tensorics.core.tensor;

import static java.util.Objects.requireNonNull;
import static org.tensorics.core.tensor.Coordinates.requireValidDimensions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;

/**
 * Default Implementation of {@link Tensor}.
 * <p>
 * By constraint of creation it holds a map of {@link Position} of certain type to values of type T, such that ALL
 * Positions contains the same number and type of coordinates. Number and type of coordinates can be accessed and
 * explored via {@link Shape}.
 * <p>
 * There is a special type of Tensor that has ZERO dimensiality. Can be obtained via factory method.
 * <p>
 * {@link ImmutableTensor} is immutable.
 * <p>
 * The toString() method does not print all the tensor entries.
 *
 * @author agorzaws, kfuchsbe
 * @param <V> type of values in Tensor.
 */
public class ImmutableTensor<V> extends AbstractTensor<V> implements Mappable<V>, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int TOSTRING_BUFFER_SIZE = 64;
    private static final int POSITION_TO_DISPLAY = 10;
    private final Map<Position, V> entries;
    private final Shape shape; // NOSONAR
    private final Position context; // NOSONAR

    /**
     * Package-private constructor to be called from builder
     *
     * @param builder to be used when {@link ImmutableTensor} is created.
     */
    ImmutableTensor(Builder<V> builder) {
        this.entries = builder.createEntriesMap();
        this.shape = Shape.viewOf(builder.dimensions(), this.entries.keySet());
        this.context = builder.context();
    }

    /**
     * Returns a builder for an {@link ImmutableTensor}. As argument it takes set of class of coordinates which
     * represent the dimensions of the tensor.
     *
     * @param dimensions a set of classes that can later be used as coordinates for the tensor entries.
     * @return a builder for {@link ImmutableTensor}
     * @param <T> type of values in Tensor.
     */
    public static final <T> Builder<T> builder(Iterable<Class<?>> dimensions) {
        return new Builder<>(requireValidDimensions(ImmutableMultiset.copyOf(dimensions)));
    }

    /**
     * Returns a builder for an {@link ImmutableTensor}. The dimensions (classes of coordinates) of the future tensor
     * have to be given as arguments here.
     *
     * @param dimensions the dimensions of the tensor to create
     * @return a builder for an immutable tensor
     * @param <T> the type of values of the tensor
     */
    public static final <T> Builder<T> builder(Class<?>... dimensions) {
        return new Builder<>(requireValidDimensions(ImmutableMultiset.copyOf(dimensions)));
    }

    /**
     * Creates a tensor from the given map, where the map has to contain the positions as keys and the values as values.
     *
     * @param dimensions the desired dimensions of the tensor. This has to be consistent with the position - keys in the
     *            map.
     * @param map the map from which to construct a tensor
     * @return a new immutable tensor
     */
    public static final <T> Tensor<T> fromMap(Iterable<Class<?>> dimensions, Map<Position, T> map) {
        Builder<T> builder = builder(dimensions);
        builder.putAll(map);
        return builder.build();
    }

    /**
     * Creates an immutable copy of the given tensor.
     *
     * @param tensor the tensor whose element to copy
     * @return new immutable Tensor
     */
    public static final <T> Tensor<T> copyOf(Tensor<T> tensor) {
        return builderFrom(tensor).build();
    }

    /**
     * Returns a builder for an {@link ImmutableTensor} which is initiliased with the given {@link ImmutableTensor}.
     *
     * @param tensor a Tensor with which the {@link Builder} is initialized
     * @return a {@link Builder} for an {@link ImmutableTensor}
     * @param <T> type of values in Tensor.
     */
    public static <T> Builder<T> builderFrom(Tensor<T> tensor) {
        Builder<T> builder = builder(tensor.shape().dimensionSet());
        builder.putAll(tensor);
        builder.context(tensor.context());
        return builder;
    }

    @Override
    public V get(Position position) {
        return findValueOrThrow(position);
    }

    @Override
    public Position context() {
        return this.context;
    }

    @Override
    public Map<Position, V> asMap() {
        /*
         * the internal map is already immutable and does not need to be copied
         */
        return entries;
    }

    @Override
    @SafeVarargs
    public final V get(Object... coordinates) {
        return get(Position.of(coordinates));
    }

    @Override
    public Shape shape() {
        return this.shape;
    }

    private V findValueOrThrow(Position position) {
        requireNonNull(position, "position must not be null");
        Positions.areDimensionsConsistentWithCoordinates(shape.dimensionSet(), position);
        V entry = findEntryOrNull(position);
        if (entry == null) {
            String message = "Entry for position '" + position + "' is not contained in this tensor.";
            Set<Class<?>> tensorDimensions = this.shape.dimensionSet();
            if (Positions.areDimensionsConsistentWithCoordinates(tensorDimensions, position)) {
                throw new NoSuchElementException(message);
            } else {
                message += "\nThe dimensions of the tensor (" + tensorDimensions
                        + ") do not match the dimensions of the requested position (" + position + ").";
                throw new IllegalArgumentException(message);
            }
        }
        return entry;
    }

    private V findEntryOrNull(Position position) {
        return this.entries.get(position);
    }

    /**
     * A builder for an immutable tensor.
     *
     * @author kfuchsbe
     * @param <V> the type of the values to be added
     */
    public static final class Builder<V> extends AbstractTensorbuilder<V> {

        private final Map<Position, V> entries = new HashMap<>();

        Builder(Set<Class<?>> dimensions) {
            super(dimensions);
        }

        /**
         * Builds the entries map as an {@link ImmutableMap}.
         *
         * @return
         */
        public Map<Position, V> createEntriesMap() {
            return ImmutableMap.<Position, V> builder().putAll(entries).build();
        }

        /**
         * Builds an {@link ImmutableTensor} from all elements put before.
         *
         * @return an {@link ImmutableTensor}.
         */
        @Override
        public ImmutableTensor<V> build() {
            return new ImmutableTensor<V>(this);
        }

        @Override
        protected void putIt(Position position, V value) {
            this.entries.put(position, value);
        }

        @Override
        public Tensorbuilder<V> remove(Position position) {
            entries.remove(position);
            return this;
        }

    }

    /**
     * When printing the tensor content output is automatically not larger then N ant the beginning and N at the end of
     * the Tensor entries.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(TOSTRING_BUFFER_SIZE);
        int totalSize = this.shape.positionSet().size();
        int index = 1;
        for (Position position : this.shape.positionSet()) {
            if (index < POSITION_TO_DISPLAY || index > totalSize - POSITION_TO_DISPLAY) {
                buffer.append(position + "=(" + get(position) + "), ");
            } else if (index == POSITION_TO_DISPLAY) {
                buffer.append(".. [" + (totalSize - 2 * POSITION_TO_DISPLAY) + " skipped entries] .. , ");
            }
            index++;
        }
        if (buffer.length() > 1) {
            buffer.setLength(buffer.length() - 2);
        }
        return Coordinates.dimensionsWithoutClassPath(this) + ", Content:{" + buffer + "}";
    }

}
