/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import java.util.function.Function;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.reduction.AbstractInterpolationStrategy;
import org.tensorics.core.reduction.InterpolatedSlicing;
import org.tensorics.core.reduction.InterpolationStrategy;
import org.tensorics.core.reduction.LinearInterpolation;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorReduction;

/**
 * @author agorzaws
 * @param <E>
 * @param <C>
 */
public class OngoingInterpolation<E, C> {

    private final Tensor<E> tensor;
    private final C slicePosition;
    private final Class<C> dimension;

    public OngoingInterpolation(C slicePosition, Tensor<E> tensor, Class<C> dimension) {
        this.tensor = tensor;
        this.slicePosition = slicePosition;
        this.dimension = dimension;
    }

    /**
     * Defines the interpolation strategy. <br>
     * See {@link AbstractInterpolationStrategy} and its extension.
     *
     * @param strategy to use
     * @return slicing result with interpolation between the missing comparable coordinates.
     */
    public Tensor<E> interpolatingWith(InterpolationStrategy<C, E> strategy) {
        return new TensorReduction<>(dimension, new InterpolatedSlicing<>(slicePosition, strategy, tensor))
                .apply(tensor);
    }
    
    /**
     * Uses a linear interpolation
     * @param field the field to interpolate in (values of the tensor)
     * @param fieldMapper a mapper from coordinates to field elements
     * @return
     */
    public Tensor<E> withLinearInterpolation(ExtendedField<E> field, Function<C,E> fieldMapper) {
    	return interpolatingWith(new LinearInterpolation<>(field, fieldMapper));
    }

}
