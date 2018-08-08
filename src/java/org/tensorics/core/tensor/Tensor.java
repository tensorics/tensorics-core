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

/**
 * The top interface for {@link Tensor} like objects.
 * 
 * @author agorzaws, kfuchsbe
 * @param <E> type of the values hold in the tensor.
 */
public interface Tensor<E> extends Tensoric<E> {

    /**
     * Retrieves the shape of the tensor. As shape we understand simply the structure of a tensor: Its dimensions and
     * the available positions.
     * <p>
     * Implementations have to take care that the returned value here is never {@code null}.
     * 
     * @return the shape of the tensor.
     */
    Shape shape();

    /**
     * Retrieves the context of the tensor, which is nothing else than a position. As context of the tensor we
     * understand coordinates within a higher dimensional space than than the tensor has itself. This coordinates can
     * e.g. transport information when e.g. a higher-dimensional tensor is split into smaller ones, so that it can
     * potentially be reconstructed afterwards.
     * <p>
     * Implementations have to guarantee that the returned value here is never {@code null}.
     * 
     * @return the context of the tensor.
     */
    Position context();

}