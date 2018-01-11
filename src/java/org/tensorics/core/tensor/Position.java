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
import static org.tensorics.core.tensor.Coordinates.requireValidCoordinates;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * Defines the position of a value within a tensor in the N-dimensional coordinate space.
 * 
 * @author agorzaws
 */
public final class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Interner<Position> INTERNER = Interners.newWeakInterner();

    /*
     * NOTE: This has to be after the cachedPositions initialization, because the 'of' method uses it!
     */
    private static final Position EMPTY_POSITION = Position.createFrom(Collections.emptySet());

    private ImmutableSet<?> coordinates;

    protected Position(Set<?> coordinates) {
        this.coordinates = ImmutableSet.copyOf(coordinates);
        for (Object oneCoordinate : coordinates) {
            if (oneCoordinate instanceof Position) {
                throw new IllegalArgumentException("A position is contained in the collection of coordinates."
                        + "This is most-probably a programming mistake and therefore not allowed.");
            }
        }
    }

    /**
     * Retrieves a position instance for the given coordinates. In order to be a valid set to create a position, the
     * coordinates must:
     * <ul>
     * <li>They must be disjunct in class hierarchy (e.g. one must not inherit from another)
     * <li>None of them must be an instance of a position
     * </ul>
     * 
     * @param coordinates the coordinates for which to get the position instance.
     * @return a position instance with the given coordinates.
     * @throws IllegalArgumentException in case the coordinates are not a valid set for creating a position.
     * @throws NullPointerException if the given coordinates are {@code null}
     */
    public static Position of(Iterable<?> coordinates) {
        requireNonNull(coordinates, "coordinates must not be null");
        return createFrom(requireValidCoordinates(coordinates));
    }

    /**
     * Retrieves a position instance, representing the given coordinates. This is a convenience method to a call to
     * {@link #of(Iterable)}.
     * 
     * @param coordinates the coordinates for which to retrieve a position instance
     * @return a position instance
     * @throws IllegalArgumentException in case the coordinates are not a valid set for creating a position.
     * @throws NullPointerException if the given coordinates array is {@code null}
     * @see #of(Iterable)
     */
    @SafeVarargs
    public static Position of(Object... coordinates) {
        requireNonNull(coordinates, "coordinates must not be null");
        return of(ImmutableMultiset.copyOf(coordinates));
    }

    private static Position createFrom(Set<?> coordinates) {
        return INTERNER.intern(new Position(coordinates));
    }

    public static Position empty() {
        return EMPTY_POSITION;
    }

    public <CS> CS coordinateFor(Class<CS> dimension) {
        return Coordinates.firstCoordinateOfTyp(coordinates, dimension);
    }

    public Set<?> coordinates() {
        return coordinates;
    }

    /**
     * Retrieves the dimensions of this position (i.e. the type of the containing coordinates).
     * <p>
     * <b>NOTE!</b> These dimensions may differ from the ones kept in the parent tensor!.
     * 
     * @return the types of the coordinates
     */
    public Set<Class<?>> dimensionSet() {
        return Coordinates.getDimensionsFrom(coordinates);
    }

    /**
     * Checks if the position is consistent with the given dimensions. Conformity means that the position contains
     * exactly one coordinate for each dimension in the given set of dimensions (classes of coordinates).
     * 
     * @param dimensions the dimensions for which conformity has to be checked.
     * @return {@code true} if the position is conform, {@code false} if not.
     */
    public boolean isConsistentWith(Set<Class<?>> dimensions) {
        Preconditions.checkArgument(dimensions != null, "Argument 'dimensions' must not be null!");
        return Positions.areDimensionsConsistentWithCoordinates(dimensions, this);
    }

    @Override
    public String toString() {
        return "Position [coordinates=" + coordinates + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
        return result;
    }

    @Override
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
        Position other = (Position) obj;
        if (coordinates == null) {
            if (other.coordinates != null) {
                return false;
            }
        } else if (!coordinates.equals(other.coordinates)) {
            return false;
        }
        return true;
    }

}