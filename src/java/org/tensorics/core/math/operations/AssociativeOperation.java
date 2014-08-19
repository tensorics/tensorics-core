/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.operations;


/**
 * Represents a binary operation which guarantees that it is associative. In other words:
 * <p>
 * Let a,b and c being elements of an mathematical structure, 'o' representing the operation. Then the following is
 * valid: (a o b) o c = a o (b o c).
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements to which this operation can be applied.
 * @see <a href="http://en.wikipedia.org/wiki/Associative">http://en.wikipedia.org/wiki/Associative</a>
 */
public interface AssociativeOperation<T> extends BinaryOperation<T> {
    /* Only a marker */
}
