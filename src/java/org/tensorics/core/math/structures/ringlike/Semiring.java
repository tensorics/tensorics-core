/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.ringlike;

import org.tensorics.core.math.structures.grouplike.CommutativeMonoid;
import org.tensorics.core.math.structures.grouplike.Monoid;

/**
 * The algebraic structure of a semiring, which has the following properties:
 * <p>
 * R is the underlying set; a, b, c are elements of R.
 * <ol>
 * <li>(R, +) is a commutative monoid with identity element 0:
 * <p>
 * (a + b) + c = a + (b + c)
 * <p>
 * 0 + a = a + 0 = a
 * <p>
 * a + b = b + a
 * <p>
 * <li>(R, *) is a monoid with identity element 1:
 * <p>
 * (a*b)*c = a*(b*c)
 * <p>
 * 1*a = a*1 = a
 * <p>
 * <li>Multiplication left and right distributes over addition:
 * <p>
 * a*(b + c) = (a*b) + (a*c) = (a + b)*c = (a*c) + (b*c)
 * <li>Multiplication by 0 annihilates R:
 * <p>
 * </ol>
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the structure
 */
public interface Semiring<T> extends RinglikeStructure<T> {

    @Override
    CommutativeMonoid<T> additionStructure();

    @Override
    Monoid<T> multiplicationStructure();

}
