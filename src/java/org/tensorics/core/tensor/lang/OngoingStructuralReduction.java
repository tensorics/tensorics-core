/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
    public Tensor<E> by(ReductionStrategy<? super C, E> strategy) {
        return reduceBy(strategy);
    }

    public Tensor<E> bySlicingAt(C slicePosition) {
        return reduceBy(new Slicing<C, E>(slicePosition));
    }

    protected Tensor<E> reduceBy(ReductionStrategy<? super C, E> strategy) {
        return new TensorReduction<>(dimension, strategy).perform(tensor);
    }

}