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

package org.tensorics.core.tensor.stream;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.tensorics.core.tensor.Position;

/**
 * Utility class to create {@link Function}s to be used to map() a stream of Entry<Position, T>. Using these convenience
 * functions avoids the user having to explicitly extract the Entry, modify it and re-build it in the end.
 * 
 * @author mihostet
 */
public final class TensorStreamMappers {
    private TensorStreamMappers() {
        /* static only */
    }

    public static <T, CI, CO> Function<Map.Entry<Position, T>, Map.Entry<Position, T>> coordinatesOfType(
            Class<CI> dimension, Function<CI, CO> coordinateMapper) {
        return entry -> {
            Position position = entry.getKey();
            CI oldCoordinate = position.coordinateFor(dimension);
            if (oldCoordinate == null) {
                throw new IllegalArgumentException(
                        "No coordinate of type " + dimension.getCanonicalName() + " in Position " + position);
            }
            CO newCoordinate = coordinateMapper.apply(oldCoordinate);
            if (position.coordinateFor(newCoordinate.getClass()) != null) {
                throw new IllegalArgumentException("Can't map to coordinate of dimension "
                        + newCoordinate.getClass().getCanonicalName() + ", already present in Position " + position);
            }
            Set<Object> coordinates = new HashSet<>(position.coordinates());
            coordinates.remove(oldCoordinate);
            coordinates.add(newCoordinate);
            return new ImmutableTensorEntry<>(Position.of(coordinates), entry.getValue());
        };
    }

    public static <T> Function<Map.Entry<Position, T>, Map.Entry<Position, T>> positions(
            Function<Position, Position> positionMapper) {
        return entry -> new ImmutableTensorEntry<>(positionMapper.apply(entry.getKey()), entry.getValue());
    }

    public static <I, O> Function<Map.Entry<Position, I>, Map.Entry<Position, O>> values(Function<I, O> valueMapper) {
        return entry -> new ImmutableTensorEntry<>(entry.getKey(), valueMapper.apply(entry.getValue()));
    }

    public static <T> Function<Map.Entry<Position, T>, Map.Entry<Position, T>> positions(
            BiFunction<Position, T, Position> positionMapper) {
        return entry -> new ImmutableTensorEntry<>(positionMapper.apply(entry.getKey(), entry.getValue()),
                entry.getValue());
    }

    public static <I, O> Function<Map.Entry<Position, I>, Map.Entry<Position, O>> values(
            BiFunction<Position, I, O> valueMapper) {
        return entry -> new ImmutableTensorEntry<>(entry.getKey(), valueMapper.apply(entry.getKey(), entry.getValue()));
    }

    private static class ImmutableTensorEntry<T> implements Map.Entry<Position, T> {

        private final Position position;
        private final T value;

        public ImmutableTensorEntry(Position position, T value) {
            this.position = position;
            this.value = value;
        }

        @Override
        public Position getKey() {
            return position;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public T setValue(T value) {
            throw new UnsupportedOperationException("this entry is immutable");
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((position == null) ? 0 : position.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ImmutableTensorEntry<?> other = (ImmutableTensorEntry<?>) obj;
            if (position == null) {
                if (other.position != null)
                    return false;
            } else if (!position.equals(other.position))
                return false;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "ImmutableTensorEntry [position=" + position + ", value=" + value + "]";
        }

    }
}
