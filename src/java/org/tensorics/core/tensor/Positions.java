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
import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
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
                if (!dimensionsToStrip.stream().anyMatch(dts -> dts.isInstance(coordinate))) {
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
    public static void assertConsistentDimensions(Position position, Set<Class<?>> dimensions) {
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
    public static boolean areDimensionsConsistentWithCoordinates(Set<Class<?>> dimensions, Position position) {
        if (position.coordinates().size() != dimensions.size()) {
            return false;
        }
        Preconditions.checkArgument(dimensions != null, "Argument '" + "dimensions" + "' must not be null!");
        Set<Class<?>> positionCoordinatesToCheck = position.dimensionSet();
        return areDimensionsConsistent(dimensions, positionCoordinatesToCheck);
    }

    public static boolean areDimensionsConsistent(Set<Class<?>> dimensions, Set<Class<?>> positionCoordinatesToCheck) {
        if (dimensions.equals(positionCoordinatesToCheck)) {
            return true;
        }
        for (Class<?> one : positionCoordinatesToCheck) {
            Coordinates.checkClassRelations(one, dimensions);
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

    /**
     * Returns all positions which can be built from all combinations of the values of the given enum classes.
     *
     * @param enumClasses the enum classes from which to get the values from
     * @return positions containing all possible combinations of enum values
     */
    @SafeVarargs
    public static final Iterable<Position> cartesianProduct(Class<? extends Enum<?>>... enumClasses) {
        return cartesianProduct(checkedToSet(enumClasses));
    }

    /**
     * Returns one position for each value of the given enum. Functionally, this is equivalent to
     * {@link #cartesianProduct(enumClass)}.
     *
     * @param enumClass the class of the enum from which to take the values as coordinates
     * @return an iterable of positions, containing the values oif the given enum class as (one-dimensional) positions.
     */
    public static final Iterable<Position> from(Class<? extends Enum<?>> enumClass) {
        return cartesianProduct(enumClass);
    }

    /**
     * Returns all positions which can be built from the values of the passed in classes that are assumed to be enum
     * classes. This is basically the runtime-checked version of {@link #cartesianProduct(Class...)}
     *
     * @param enumClasses the enum classes from which to get their values
     * @return an iterable containing the positions which are constructed from the values of the enums
     * @throws IllegalArgumentException in case not all clases are enums
     */
    public static final Iterable<Position> cartesianEnumProduct(Class<?>... enumClasses) {
        return cartesianEnumProduct(checkedToSet(enumClasses));
    }

    /**
     * Returns all positions which can be built from all combinations of the values of the given enum classes.
     *
     * @param enumClasses the enums whose values will be used to create the positions
     * @return an iterable with positions containing all possible combinations of the enum values
     */
    public static Iterable<Position> cartesianProduct(Set<Class<? extends Enum<?>>> enumClasses) {
        List<Set<?>> valueSets = enumClasses.stream().map(c -> ImmutableSet.copyOf(c.getEnumConstants()))
                .collect(Collectors.toList());
        return cartesianProduct(valueSets);
    }

    /**
     * Returns all positions which can be built from the values of the passed in classes that are assumed to be enum
     * classes. This is basically the runtime-checked version of {@link #cartesianProduct(Set)}
     *
     * @param classes the enum classes from which to get their values
     * @return an iterable containing the positions which are constructed from the values of the enums
     * @throws IllegalArgumentException in case not all clases are enums
     */
    public static Iterable<Position> cartesianEnumProduct(Set<Class<?>> classes) {
        return cartesianProduct(checkedToEnumClassSet(classes));
    }

    private static Set<Class<? extends Enum<?>>> checkedToEnumClassSet(Set<Class<?>> classes) {
        Set<Class<? extends Enum<?>>> enumClasses = new HashSet<>();
        for (Class<?> aClass : classes) {
            if (!aClass.isEnum()) {
                throw new IllegalArgumentException("All provided classes have to be enum classes. However (at least) "
                        + aClass.getCanonicalName() + "is not!");
            }
            @SuppressWarnings("unchecked")
            Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) aClass;
            enumClasses.add(enumClass);
        }
        return enumClasses;
    }

    private static <T> Set<T> checkedToSet(T[] enumClasses) {
        Set<T> classesSet = ImmutableSet.copyOf(enumClasses);
        if (classesSet.size() != enumClasses.length) {
            throw new IllegalArgumentException("The number of passed in classes is " + enumClasses.length
                    + ", but there seem to be only " + classesSet.size() + "different ones.");
        }
        return classesSet;
    }

    private static Iterable<Position> cartesianProduct(List<Set<?>> coordinateSets) {
        Set<List<Object>> cartesianProduct = Sets.cartesianProduct(ImmutableList.copyOf(coordinateSets));
        return cartesianProduct.stream().map(l -> Position.of(new HashSet<>(l))).collect(toSet());
    }

}
