/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.predicates;

/**
 * Represents a predicate (boolean-valued function) of two arguments of the same type. This is a functional interface
 * whose functional method is {@link #test(Object, Object)}.
 * 
 * @author kfuchsbe
 * @param <T> the type of the operands
 */
public interface BinaryPredicate<T> {

    /**
     * Evaluates the predicate (condition) on the given arguments.
     * 
     * @param left the left operator of the condition
     * @param right the right operator of the condition
     * @return {@code true} if the predicate is fulfilled, {@code false} otherwise.
     */
    boolean test(T left, T right);

}
