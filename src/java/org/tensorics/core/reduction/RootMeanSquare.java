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

import org.tensorics.core.iterable.lang.ScalarIterableSupport;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.Position;

/**
 * Performs an rms calculation over all in one direction of a tensor, if used for tensor reduction.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which all the operations are based on.
 */
public class RootMeanSquare<S> extends ScalarIterableSupport<S> implements ReductionStrategy<Object, S, S> {

    public RootMeanSquare(ExtendedField<S> field) {
        super(field);
    }

    @Override
    public S reduce(Map<? extends Object, S> inputValues, Position position) {
        return rmsOf(inputValues.values());
    }

    @Override
    public Position context(Position originalContext) {
        return originalContext;
    }

}
