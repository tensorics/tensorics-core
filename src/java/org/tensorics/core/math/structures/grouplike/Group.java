/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

import org.tensorics.core.math.operations.UnaryOperation;

/**
 * Represents the algebraic structure of a group, which has with respect to a monoid the additional property that it
 * provides a unitary operation, which gives rise to inverse elements.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the group
 * @see <a href="http://en.wikipedia.org/wiki/Group_(mathematics)">http://en.wikipedia.org/wiki/Group_(mathematics)</a>
 */
public interface Group<T> extends Monoid<T> {

    /**
     * Has to return the inversion operation for elements.
     * 
     * @return the inversion operation
     */
    UnaryOperation<T> inversion();
}
