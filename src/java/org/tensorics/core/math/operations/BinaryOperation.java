/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.operations;

/**
 * An operation for two values, which can be used in algebraic structures.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the algebraic structures.
 */
public interface BinaryOperation<T> {

    /**
     * Has to be implemented to perform the actual operation. The order of operands might be important or not,
     * depending, if the operation is commutative or not.
     * 
     * @param left the left operand to be used in the operation
     * @param right the right operand to be used in the operation
     * @return the result of the operation
     */
    T perform(T left, T right);
}
