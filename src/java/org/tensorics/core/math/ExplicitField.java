/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math;

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.math.predicates.BinaryPredicate;

/**
 * A more explicit view on the algebraic structure of a field. It provides more dedicated operations.
 * 
 * @param <T> the type of the elements of the field
 */
public interface ExplicitField<T> {

    /**
     * Has to return the '+' operation.
     * 
     * @return the operation which can perform a + b.
     */
    BinaryOperation<T> addition();

    UnaryOperation<T> additiveInversion();

    BinaryOperation<T> subtraction();

    T zero();

    /**
     * Has to return the '*' operation.
     * 
     * @return the operation which can perform a * b.
     */
    BinaryOperation<T> multiplication();

    BinaryOperation<T> division();

    UnaryOperation<T> multiplicativeInversion();

    T one();

    T two();
    
    BinaryPredicate<T> less();
    
    BinaryPredicate<T> lessOrEqual();
    
    BinaryPredicate<T> equal();
    
    BinaryPredicate<T> greaterOrEqual();
    
    BinaryPredicate<T> greater();
    

}