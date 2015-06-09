/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import java.util.Map;

/**
 * The top interface for {@link Tensor} like objects.
 * 
 * @author agorzaws, kfuchsbe
 * @param <E> type of the values hold in the tensor.
 */
public interface Tensor<E> {

    /**
     * @param position the position in the N-dimensional space where to find the value.
     * @return the value at the given position
     * @throws IllegalArgumentException when number of coordinates is not sufficient
     * @throws java.util.NoSuchElementException if the tensor contains no element for the given position
     */
    E get(Position position);

    /**
     * @param coordinates form N-dimensional space where to find the value.
     * @return a value at the given coordinates.
     * @throws IllegalArgumentException if the number of coordinates in incorrect
     * @throws java.util.NoSuchElementException if the tensor contains no element for the given position
     */
    E get(Object... coordinates);

    /**
     * @return entry set of the tensor.
     */
    Iterable<Entry<E>> entrySet();

    /**
     * @return the shape of the tensor. The RAW coordinates structure, no tensor values are returend here.
     */
    Shape shape();

    /**
     * @return a Context of the tensor.
     */
    Context context();

    /**
     * An interface for tensor entries.
     * 
     * @author agorzaws
     * @param <E> type of the values in the tensor.
     */
    interface Entry<E> {

        /**
         * @return a value of the entry, type <E>
         */
        E getValue();

        /**
         * @return position of the entry.
         */
        Position getPosition();

    }

}