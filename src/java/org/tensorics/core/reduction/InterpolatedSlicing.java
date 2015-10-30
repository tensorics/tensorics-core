/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction;

import java.util.Map;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

/**
 * The extended slicing strategy that involves the possibility to interpolate over the specified, comparable coordinate
 * 
 * @author agorzaws
 * @param <E> the type of the hold values
 * @param <C> the type of the interpolation direction, must be Comparable
 */
public class InterpolatedSlicing<C extends Comparable<C>, E> extends Slicing<C, E> {

    private final InterpolationStrategy<C, E> strategy;
    private final Tensor<E> tensor;

    public InterpolatedSlicing(C slicePosition, InterpolationStrategy<C, E> strategy, Tensor<E> tensor) {
        super(slicePosition);
        this.strategy = strategy;
        this.tensor = tensor;
    }

    @Override
    public E reduce(Map<? extends C, E> inputValues, Position position) {
        if (inputValues.get(slicePosition) == null) {
            Tensor<E> extractedToInterpolate = Tensorics.from(tensor).extract(position);
            return strategy.getInterpolatedValue(extractedToInterpolate, slicePosition);
        } else {
            return inputValues.get(slicePosition);
        }
    }
}
