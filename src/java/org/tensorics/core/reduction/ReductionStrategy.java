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

package org.tensorics.core.reduction;

import java.util.Map;

import org.tensorics.core.tensor.Position;

/**
 * A strategy which reduces the one dimension (= type of coordinate) and summarizes all the values in this direction
 * into one. Typical examples of this are, e.g. summing up one dimension, or just slicing out at one coordinate.
 * 
 * @author kfuchsbe, agorzaws
 * @param <C> The type of the coordinate (aka 'the dimension') which should be reduced.
 * @param <T> The type of values that can be reduced by this strategy
 * @param <R> The type of the values after the reduction
 */
public interface ReductionStrategy<C, T, R> {

    /**
     * @param inputValues the sub values of the tensor from which the reduction is performed
     * @param position a remaining position in the tensor for which reduction is performed
     * @return the value of reduction
     */
    R reduce(Map<? extends C, T> inputValues, Position position);

    Position context(Position originalContext);
}
