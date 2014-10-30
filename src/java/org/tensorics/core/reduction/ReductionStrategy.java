/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction; 

import java.util.Map;

import org.tensorics.core.tensor.Position;

/**
 * A strategy which reduces the one dimension (= type of coordinate) and summarizes all the values in this direction
 * into one. Typical examples of this are, e.g. summing up one dimension, or just slicing out at one coordinate.
 * 
 * @author kfuchsbe
 * @param <C> The type of the coordinate (aka 'the dimension') which should be reduced.
 * @param <T> The type of values that can be reduced by this strategy
 */
public interface ReductionStrategy<C, T> {

    T reduce(Map<? extends C, T> inputValues);
    
    Position context();
}
