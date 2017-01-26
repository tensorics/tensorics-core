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

import org.tensorics.core.reduction.ReductionStrategy;
import org.tensorics.core.reduction.Slicing;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorReduction;

/**
 * Part of the tensoric fluent API, which provides methods to specify concretely how a given dimension should be
 * reduced.
 * 
 * @author kfuchsbe
 * @param <C> the type of coordinate (aka 'the dimension') which is going to be reduced (by calling methods of this
 *            class)
 * @param <E> the type of the elements of the tensor to be reduced.
 */
public class OngoingStructuralReduction<C, E> {

    private final Tensor<E> tensor;
    private final Class<C> dimension;

    public OngoingStructuralReduction(Tensor<E> tensor, Class<C> dimension) {
        this.tensor = tensor;
        this.dimension = dimension;
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public <R> Tensor<R> by(ReductionStrategy<? super C, E, R> strategy) {
        return reduceBy(strategy);
    }

    public Tensor<E> bySlicingAt(C slicePosition) {
        return reduceBy(new Slicing<C, E>(slicePosition));
    }

    public OngoingStructuralReductionOptions<E, C> byInterpolatedSlicingAt(C slicePosition) {
        return new OngoingStructuralReductionOptions<>(slicePosition, tensor, dimension);
    }

    protected <R> Tensor<R> reduceBy(ReductionStrategy<? super C, E, R> strategy) {
        return new TensorReduction<>(dimension, strategy).apply(tensor);
    }

}