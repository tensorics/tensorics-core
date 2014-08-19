/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.operations;

/**
 * A binary operation which guarantees both, associativity and commutativity.
 * 
 * @author kfuchsbe
 * @param <T> the type of elements on which the operation can be applied
 */
public interface CommutativeAssociativeOperation<T> extends CommutativeOperation<T>, AssociativeOperation<T> {
    /* Only marks a dedicated type, to combine both interfaces */
}
