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
import org.tensorics.core.tensor.Positions;

/**
 * A reduction strategy, which returns all values of which are at one exact value of the dimension to be reduced.
 * 
 * @author kfuchsbe
 * @param <C> the type of coordinate (aka 'the dimension') do be reduced
 * @param <E> the type of the tensor elements
 */
public class Slicing<C, E> implements ReductionStrategy<C, E, E> {

    protected final C slicePosition;

    public Slicing(C slicePosition) {
        this.slicePosition = slicePosition;
    }

    @Override
    public E reduce(Map<? extends C, E> inputValues, Position position) {
        return inputValues.get(slicePosition);
    }

    @Override
    public Position context(Position originalContext) {
        return Positions.union(originalContext, Position.of(slicePosition));
    }

}
