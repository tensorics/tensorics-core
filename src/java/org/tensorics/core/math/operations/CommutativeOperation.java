/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.operations;


/**
 * Represents a binary operation on elements of a set, where the following condition is fullfilled:
 * <p>
 * a o b = b o a, with 'a', 'b' being elements of the underlying set, 'o' being the operation.
 * 
 * @author kfuchsbe
 * @param <T>
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Commutative_property">http://en.wikipedia.org/wiki/Commutative_property</a>
 */
public interface CommutativeOperation<T> extends BinaryOperation<T> {
    /* only a marker */
}
