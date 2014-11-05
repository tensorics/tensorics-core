/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
public class Slicing<C, E> implements ReductionStrategy<C, E> {

    private final C slicePosition;

    public Slicing(C slicePosition) {
        this.slicePosition = slicePosition;
    }

    @Override
    public E reduce(Map<? extends C, E> inputValues) {
        return inputValues.get(slicePosition);
    }

    @Override
    public Position context(Position originalContext) {
        return Positions.union(originalContext, Position.of(slicePosition));
    }

}
