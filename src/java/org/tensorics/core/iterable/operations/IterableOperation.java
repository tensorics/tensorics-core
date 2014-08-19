/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import org.tensorics.core.commons.operations.Conversion;

/**
 * An operation which takes one iterable as input and returns one value, corresponding to a super type of the iterable
 * elements.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the field, on which the operations are based on.
 */
public interface IterableOperation<T> extends Conversion<Iterable<T>, T> {
    /* Nothing special here, inherits everything from ConversionOperation */
}
