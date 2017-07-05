/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction;

import java.util.Map;

import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.MapBackedDiscreteFunction;
import org.tensorics.core.tensor.Position;

/**
 * A reduction strategy which reduces all elements of dimension {@code <X>} by transforming them into a
 * {@link DiscreteFunction} s from {@code <X>} to {@code <Y>}
 * 
 * @author kfuchsbe, caguiler
 * @param <X> the type of the values along the X-axis, the type of coordinate (aka 'the dimension') do be reduced
 * @param <Y> the type of the tensor elements and also the type of the values along the Y-axis
 */
public final class ToFunctions<X, Y> implements ReductionStrategy<X, Y, DiscreteFunction<X, Y>> {

    @Override
    public DiscreteFunction<X, Y> reduce(Map<? extends X, Y> inputValues, Position position) {
        return MapBackedDiscreteFunction.fromMap(inputValues);
    }

    @Override
    public Position context(Position originalContext) {
        return originalContext;
    }
}