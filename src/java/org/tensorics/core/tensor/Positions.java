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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

/**
 * Contains utility methods for position objects.
 * 
 * @author kfuchsbe
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Positions {

    /**
     * private constructor to avoid instantiation
     */
    private Positions() {
        /* Only static methods */
    }

    /**
     * Constructs a position, whose coordinates are the union of the coordinates of the two individual positions. This
     * is only correctly defined, if the two given positions do not have any dimensional overlap (i.e. Any coordinate of
     * a certain type (class) is only present in either the left or the right position.)
     * 
     * @param left the first position to use construct the union position
     * @param right the second position to construct the union position
     * @return a position, which contains the union of the coordinates of the two input positions.
     * @throws NullPointerException if one of the arguments is {@code null}
     * @throws IllegalArgumentException if the given aguments have an overlap of dimensions and therefor the union of
     *             the position is not well defined
     */
    public static Position union(Position left, Position right) {
        checkNotNull(left, "left position must not be null.");
        checkNotNull(right, "right position must not be null.");
        checkArgument(Sets.intersection(left.dimensionSet(), right.dimensionSet()).isEmpty(),
                "Positions have overlapping dimensions. It is not possible to create the union of them.");
        SetView<Object> coordinates = Sets.union(left.coordinates(), right.coordinates());
        return Position.of(coordinates);
    }

    /**
     * Copies the given positions to a set, to be sure that each element is contained only once
     * 
     * @param positions the positions, which shall be ensured that they are unique
     * @return a set of unique positions
     */
    public static Set<Position> unique(Iterable<Position> positions) {
        return ImmutableSet.copyOf(positions);
    }

    /**
     * Factory method for a dimension stripper.
     * 
     * @param dimensionsToStrip the dimensions which shall be stripped from the positions passed to the stripper.
     * @return a function object that can strip the given dimensions from positions.
     */
    public static Positions.DimensionStripper stripping(final Set<? extends Class<?>> dimensionsToStrip) {
        return new Positions.DimensionStripper(dimensionsToStrip);
    }

    /**
     * Factory method for a dimension stripper with one dimension to strip only
     * 
     * @param dimensionsToStrip the dimension to strip
     * @return a dimension stripper stripping exactly one dimension
     * @see #stripping(Set)
     */
    public static Positions.DimensionStripper stripping(Class<?> dimensionsToStrip) {
        return new Positions.DimensionStripper(ImmutableSet.of(dimensionsToStrip));
    }

    /**
     * A functional object to transform positions to other positions with the dimensions stripped as given in the
     * constructor.
     * 
     * @author kaifox
     */
    public static class DimensionStripper implements Function<Position, Position> {

        private final Set<? extends Class<?>> dimensionsToStrip;

        DimensionStripper(Set<? extends Class<?>> dimensionsToStrip) {
            this.dimensionsToStrip = ImmutableSet.copyOf(dimensionsToStrip);
        }

        @Override
        public Position apply(Position position) {
            Builder<Object> builder = ImmutableSet.builder();
            for (Object coordinate : position.coordinates()) {
                if (!dimensionsToStrip.contains(coordinate.getClass())) {
                    builder.add(coordinate);
                }
            }
            return Position.of(builder.build());
        }

    }

    /**
     * Checks if the given position is conform with the given coordinate and throws an exception, if not.
     * 
     * @param position the position for which to check the consistency
     * @param dimensions the dimensions for which the conformity has to be verified.
     * @throws IllegalArgumentException if the position is not conform
     * @see Position#isConsistentWith(Set)
     */
    public static void assertConsistentDimensions(Position position, Set<? extends Class<?>> dimensions) {
        if (!position.isConsistentWith(dimensions)) {
            throw new IllegalArgumentException(
                    "The given coordinates are not consistent with the dimensions of the tensor! position='" + position
                            + "'; required dimensions='" + dimensions + "'.");
        }
    }

    /**
     * Searches if given position coordinates match acceptable dimensions.
     * 
     * @param dimensions
     * @param position
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <C extends Class<?>> boolean areDimensionsConsistentWithCoordinates(Set<C> dimensions,
            Position position) {
        if (position.coordinates().size() != dimensions.size()) {
            return false;
        }
        Preconditions.checkArgument(dimensions != null, "Argument '" + "dimensions" + "' must not be null!");
        Set<?> positionCoordinatesToCheck = position.dimensionSet();
        if (dimensions.equals(positionCoordinatesToCheck)) {
            return true;
        }
        for (Object one : positionCoordinatesToCheck) {
            Coordinates.checkClassRelations((C) one, dimensions);
        }
        return true;
    }

    /**
     * Combines the both positions of the pair in such a way, that for each coordinate of the types given in the given
     * set of dimensions have to be
     * <ul>
     * <li>either present in both positions of the pair, and then have to be the same
     * <li>or be present in only one of the both positions
     * </ul>
     * 
     * @param pair the pair, whose dimensions should be united
     * @param targetDimensions the dimension in which the positions shall be united
     * @return a new position, containing the coordinates of the pair, merged by the above rules
     */
    /* Similar to union ... maybe to be unified at some point */
    public static Position combineDimensions(PositionPair pair, Set<Class<?>> targetDimensions) {
        Position left = pair.left();
        Position right = pair.right();
        return combineDimensions(left, right, targetDimensions);
    }

    /**
     * Combines the both positions in such a way, that for each coordinate of the types given in the given set of
     * dimensions have to be
     * <ul>
     * <li>either present in both positions of the pair, and then have to be the same
     * <li>or be present in only one of the both positions
     * </ul>
     * 
     * @param left the first of the two positions, whose dimensions should be united
     * @param right the second of the two positions whose dimensions should be combined
     * @param targetDimensions the dimension in which the positions shall be united
     * @return a new position, with the coordinates merged according to the above rules
     */
    public static Position combineDimensions(Position left, Position right, Set<Class<?>> targetDimensions) {
        ImmutableSet.Builder<Object> builder = ImmutableSet.builder();
        for (Class<?> dimension : targetDimensions) {
            Object leftCoordinate = left.coordinateFor(dimension);
            Object rightCoordinate = right.coordinateFor(dimension);
            if (Objects.equal(leftCoordinate, rightCoordinate) || oneIsNull(leftCoordinate, rightCoordinate)) {
                builder.add(MoreObjects.firstNonNull(leftCoordinate, rightCoordinate));
            } else {
                throw new IllegalArgumentException(
                        "Coordinates for dimension '" + dimension + "' are neither the same in both positions (" + left
                                + " and " + right + "), nor present only in one. Cannot consistently combine.");
            }
        }
        return Position.of(builder.build());
    }

    public static <T> boolean oneIsNull(T left, T right) {
        return ((left == null) || (right == null));
    }

    /**
     * Combines all position pairs into positions containing the given dimensions and returns a map from the combined
     * positions to the original position pairs.
     * 
     * @param positionPairs the position pairs to combine the final positions
     * @param targetDimensions the dimensions in which to combine the positions
     * @return a map from the combined dimensions to the original pairs
     */
    public static ImmutableSetMultimap<Position, PositionPair> combineAll(Set<PositionPair> positionPairs,
            Set<Class<?>> targetDimensions) {
        ImmutableSetMultimap.Builder<Position, PositionPair> builder = ImmutableSetMultimap.builder();
        for (PositionPair pair : positionPairs) {
            builder.put(combineDimensions(pair, targetDimensions), pair);
        }
        return builder.build();
    }

    public static Multimap<Position, Position> mapByStripping(Iterable<Position> positions,
            Set<Class<?>> dimensionsToStrip) {
        DimensionStripper stripper = stripping(dimensionsToStrip);
        ImmutableMultimap.Builder<Position, Position> builder = ImmutableMultimap.builder();
        for (Position position : positions) {
            builder.put(stripper.apply(position), position);
        }
        return builder.build();
    }

    /**
     * Extracts the provided class of the coordinate from the set of the positions.
     * 
     * @param positions
     * @param ofClass a class of the coordinate to be extracted
     * @return a set of the extracted coordinates from provided positions
     */
    public static <T> Set<T> coordinatesOfType(Set<Position> positions, Class<T> ofClass) {
        Set<T> toReturn = new HashSet<>();
        for (Position onePosition : positions) {
            T coordinate = onePosition.coordinateFor(ofClass);
            if (coordinate != null) {
                toReturn.add(coordinate);
            }
        }
        return toReturn;
    }

}
