/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

/**
 * Represents the algebraic structure of a Monoid, which extends a semigroup by having an identity element.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the set on which the monoid is based
 * @see <a href="http://en.wikipedia.org/wiki/Monoid">http://en.wikipedia.org/wiki/Monoid</a>
 */
public interface Monoid<T> extends Semigroup<T> {

    /**
     * Has to return the element of the set, which represents the identity of the operation of the monoid.
     * 
     * @return the identity element of the monoid.
     */
    T identity();
}
