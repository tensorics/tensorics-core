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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Defines the shape of a tensor. This implies all the positions for which the tensor stores values.
 * <p>
 * This class is immutable
 * 
 * @author kfuchsbe
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
public final class Shape implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Shape EMPTY_SHAPE = of();
    private static final Shape ZERO_DIMENSIONAL_SHAPE = of(Position.empty());

    private final Set<? extends Class<?>> dimensions;
    private final Set<Position> positions;

    Shape(Builder builder) {
        this(ImmutableSet.copyOf(builder.dimensions), ImmutableSet.copyOf(builder.setBuilder.build()));
    }

    Shape(Set<? extends Class<?>> dimensions, Set<Position> positions) {
        this.dimensions = dimensions;
        this.positions = positions;
    }

    /**
     * Retrieves all the positions which are available in a tensor for this shape.
     * 
     * @return an immutable set of the positions for a tensor of this shape.
     */
    public Set<Position> positionSet() {
        return new HashSet<>(this.positions);
    }

    /**
     * Retrieves all the dimensions of this shape.
     * 
     * @return the dimensions
     */
    public Set<Class<?>> dimensionSet() {
        return new HashSet<>(this.dimensions);
    }

    /**
     * Returns the number of dimensions of a tensor of this shape. Other words are 'rank' or 'order' of the tensor. This
     * has to be consistent with the size of the collections returned by {@link #dimensionSet()};
     * 
     * @return the number of dimensions of the tensor (rank, order)
     */
    public int dimensionality() {
        return this.dimensions.size();
    }

    /**
     * Returns the number of entries of a tensor of this shape. This has to be consistent with the size of the
     * collection returned by {@link #positionSet()};
     * 
     * @return the number of positions.
     */
    public int size() {
        return this.positions.size();
    }

    /**
     * returns {@code true} if this shape contains at least all the positions of the the other.
     * 
     * @param other the set which to compare to.
     * @return {@code true} if the other shape is covered, {@code false} otherwise.
     */
    public boolean covers(Shape other) {
        return this.positions.containsAll(other.positionSet());
    }

    /**
     * Returns {@code true} if the shape contains the given position, {@code false} otherwise.
     * 
     * @param position the position to be checked
     * @return {@code true} if the position is contained in the shape, {@code false} otherwise.
     */
    public boolean contains(Position position) {
        return this.positions.contains(position);
    }

    /**
     * Convenience method for {@link #contains(Position)}, with the position constructed from the given coordinates.
     * 
     * @param coordinates the coordinates which represent the position to be checked
     * @return {@code true} if the position represented by the given coordinates is contained in the shape,
     *         {@code false} otherwise.
     */
    public boolean contains(Object... coordinates) {
        return contains(Position.of(coordinates));
    }

    /**
     * returns {@code true} if this shape has the same dimensions as the other shape.
     * 
     * @param other the other shape with which to compare this shape
     * @return {@code true} if the dimensions are the same, {@code false} otherwise
     */
    public boolean hasSameDimensionsAs(Shape other) {
        return this.dimensionSet().equals(other.dimensionSet());
    }

    /**
     * Creates a new builder for a shape. The dimensions of the resulting shape will be retrieved from the first
     * position that will be added. If no elements will be added the resulting shape will have zero dimensions.
     * 
     * @return a new builder for a shape.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new builder for a shape with predefined dimensions.
     * 
     * @param dimensions the dimensions for the new shape
     * @return the builder with predefined dimensions
     */
    public static Builder builder(Set<? extends Class<?>> dimensions) {
        return new Builder(dimensions);
    }

    /**
     * Directly creates a shape using the given dimensions and positions. The consistency of dimensions and positions is
     * not checked! Thus this is only for internal use.
     * 
     * @param dimensions the dimensions for the shape
     * @param positions the positions of the shape
     * @return a new shape instance
     */
    public static Shape viewOf(Set<? extends Class<?>> dimensions, Set<Position> positions) {
        return new Shape(dimensions, positions);
    }

    /**
     * Creates a shape from the given set of positions. The dimensions (types of coordinates) are taken from the first
     * element in the provided iterable. If the given positions do not correspond to the same dimensions, then an
     * {@link IllegalArgumentException} will be thrown.
     * 
     * @param positions the positions which shall be used for the new shape
     * @return a new shape, containing the given positions
     * @throws IllegalArgumentException if the dimensions of the individual positions are not consistent.
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public static Shape of(Iterable<Position> positions) {
        return builder().addAll(positions).build();
    }

    /**
     * This is a convenience method to be used instead of {@link #of(Iterable)}.
     * 
     * @param positions the positions for the new shape
     * @return a new shape containing the given positions
     * @see #of(Iterable)
     * @throws IllegalArgumentException if the dimensions of the positions are inconsistent
     */
    @SafeVarargs
    @SuppressWarnings("PMD.ShortMethodName")
    public static Shape of(Position... positions) {
        return of(Arrays.asList(positions));
    }

    /**
     * Returns an empty shape with no dimensions and no positions. The returned shape is equal to the shape of an zero
     * dimensional tensor. It is further equivalent to the call to {@code of()} (with no arguments). However, using this
     * method is preferred, since no unnecessary instances are created this way.
     * 
     * @return an empty shape
     */
    public static Shape empty() {
        return EMPTY_SHAPE;
    }

    /**
     * Returns a shape corresponding to a zero-dimensional tensor with one entry (an empty position).
     * 
     * @return a shape corresponding to a zero - dimensional tensor with one entry (at {@link Position#empty()})
     */
    public static Shape zeroDimensional() {
        return ZERO_DIMENSIONAL_SHAPE;
    }

    /**
     * Extracts the provided class of the coordinate from the provided shape.
     * 
     * @param ofClass a class of the coordinate to be extracted
     * @return a set of the extracted coordinates from provided shape
     */
    public <T> Set<T> coordinatesOfType(Class<T> ofClass) {
        return Positions.coordinatesOfType(positions, ofClass);
    }

    /**
     * The builder for a tensor shape. It has to be instantiated with a set of dimensions and provides methods to add
     * positions to the shape. It is only allowed to add positions which provide exactly one coordinate per dimension as
     * given in the constructor of the builder. To create a shape finally, the {@link #build()} method has to be called.
     * <p>
     * This class is not thread safe!
     * 
     * @author kfuchsbe
     */
    public static final class Builder {

        private Set<Class<?>> dimensions = Collections.emptySet();
        private boolean dimensionsDefined = false;
        private final ImmutableSet.Builder<Position> setBuilder = ImmutableSet.builder();

        Builder() {
            /* Nothing to do, just make the constructor package private */
        }

        Builder(Set<? extends Class<?>> dimensions) {
            setDimensionsIfUndefined(dimensions);
        }

        public Builder add(Position position) {
            setDimensionsIfUndefined(position.dimensionSet());
            Positions.assertConsistentDimensions(position, dimensions);
            this.setBuilder.add(position);
            return this;
        }

        public Builder addAll(Iterable<Position> positions) {
            for (Position position : positions) {
                add(position);
            }
            return this;
        }

        public Shape build() {
            return new Shape(this);
        }

        private void setDimensionsIfUndefined(Set<? extends Class<?>> dimensions) {
            if (!dimensionsDefined) {
                this.dimensions = ImmutableSet.copyOf(dimensions);
                this.dimensionsDefined = true;
            }
        }

    }

    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Shape other = (Shape) obj;
        if (dimensions == null) {
            if (other.dimensions != null) {
                return false;
            }
        } else if (!dimensions.equals(other.dimensions)) {
            return false;
        }
        if (positions == null) {
            if (other.positions != null) {
                return false;
            }
        } else if (!positions.equals(other.positions)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dimensions == null) ? 0 : dimensions.hashCode());
        result = prime * result + ((positions == null) ? 0 : positions.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Shape [dimensions=" + dimensions+ ", #positions=" + positions.size() + "]";
    }

}
