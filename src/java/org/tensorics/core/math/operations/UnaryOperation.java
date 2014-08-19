/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.operations;

/**
 * The general form of an unitary operation, to be used in algebraic structures.
 * 
 * @author kfuchsbe
 * @param <T> the type of values for which the unitary operation can be applied
 */
public interface UnaryOperation<T> {

    /**
     * performs the operation on the given value
     * 
     * @param value the input value, on which the operation shall be applied
     * @return the resulting value after the operation
     */
    T perform(T value);
}
