/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

/**
 * The algebraic structure of a commutative monoid (less common an 'abelian monoid'), meaning that the operation is
 * commutative and associative.
 * 
 * @author kfuchsbe
 * @param <T> The type of the elements of the underlying set
 * @see <a href="http://en.wikipedia.org/wiki/Commutative_monoid#Commutative_monoid">
 *      http://en.wikipedia.org/wiki/Commutative_monoid#Commutative_monoid</a>
 */
public interface CommutativeMonoid<T> extends CommutativeSemigroup<T>, Monoid<T> {
    /* All operations defined from super interfaces */
}
