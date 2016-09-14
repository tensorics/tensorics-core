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
    @Deprecated
    Iterable<Entry<E>> entrySet();

    /**
     * @return all the entries as a map of Position to value. There is no guarantee about the thread safety or
     *         mutability of the returned {@link Map}.
     */
    Map<Position, E> asMap();

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
    @Deprecated
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