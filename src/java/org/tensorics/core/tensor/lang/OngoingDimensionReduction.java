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

package org.tensorics.core.tensor.lang;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.reduction.Averaging;
import org.tensorics.core.reduction.RootMeanSquare;
import org.tensorics.core.tensor.Tensor;

/**
 * Part of the tensorics fluent API, that allows to further describe how a tensor dimesion shall be reduced (where the
 * field was not yet known in the previous expression part)
 * 
 * @author kfuchsbe
 * @param <C> the type of the dimension in which the tensor shall be reduced
 * @param <S> the type of the scalars (elements of the field on which all the operations are based on)
 */
public final class OngoingDimensionReduction<C, S> extends OngoingStructuralReduction<C, S> {

    public OngoingDimensionReduction(Tensor<S> tensor, Class<C> dimension) {
        super(tensor, dimension);
    }

    public Tensor<S> byAveragingIn(ExtendedField<S> field) {
        return reduceBy(new Averaging<>(field));
    }

    public Tensor<S> byRmsIn(ExtendedField<S> field) {
        return reduceBy(new RootMeanSquare<>(field));
    }
}