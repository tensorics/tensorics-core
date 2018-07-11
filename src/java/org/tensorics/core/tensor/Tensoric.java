/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

/**
 * The most generic interface of a multidimensional object within tensorics. Values can be retrieved from it and it
 * knows for which positions it contains values.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the tensoric object
 */
public interface Tensoric<V> {

    /**
     * @param position the position in the N-dimensional space where to find the value.
     * @return the value at the given position
     * @throws IllegalArgumentException when number of coordinates is not sufficient
     * @throws java.util.NoSuchElementException if the tensor contains no element for the given position
     */
    V get(Position position);

    /**
     * @param coordinates form N-dimensional space where to find the value.
     * @return a value at the given coordinates.
     * @throws IllegalArgumentException if the number of coordinates in incorrect
     * @throws java.util.NoSuchElementException if the tensor contains no element for the position constructed from the
     *             given coordinates.
     */
    default V get(Object... coordinates) {
        return get(Position.of(coordinates));
    }

    /**
     * Returns {@code true} if the tensoric object contains the given position, {@code false} otherwise.
     * 
     * @param position the position to be checked
     * @return {@code true} if the position is contained in the tensoric object, {@code false} otherwise.
     */
    public boolean contains(Position position);

    /**
     * Convenience method for {@link #contains(Position)}, with the position constructed from the given coordinates.
     * 
     * @param coordinates the coordinates which represent the position to be checked
     * @return {@code true} if the position represented by the given coordinates is contained in the tensoric object,
     *         {@code false} otherwise.
     */
    default boolean contains(Object... coordinates) {
        return contains(Position.of(coordinates));
    }

}
