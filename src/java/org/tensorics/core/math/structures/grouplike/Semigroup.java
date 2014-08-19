/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike; 

import org.tensorics.core.math.operations.AssociativeOperation;

/**
 * Represents the algebraic structure of a semigroup (the same as an associative magma). This means that it the
 * operation is an associative one.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the semigroup
 * @see <a href="http://en.wikipedia.org/wiki/Semigroup">http://en.wikipedia.org/wiki/Semigroup</a>
 */
public interface Semigroup<T> extends Magma<T> {

    @Override
    AssociativeOperation<T> operation();

}
