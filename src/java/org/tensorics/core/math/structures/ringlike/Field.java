/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.ringlike;

import org.tensorics.core.math.structures.grouplike.AbelianGroup;

/**
 * Represents the algebraic structure 'field'. It means that it represents a set of elements together with two binary
 * operations (+, *) with the following properties:
 * <ul>
 * <li>both, + and * are associative: a + (b + c) = (a + b) + c; a * (b * c) = (a * b) * c.
 * <li>both, + and * have an identity element (Called '0' for +, '1' for *): a + 0 = a; a * 1 = a.
 * <li>both, + and * have an inverse element (Called '-a' for +, '1/a' for *): a + (-a) = 0; a * 1/a = 1.
 * <li>both, + and * are commutative: a + b = b + a; a * b = b * a.
 * <li>* is distributive over +: a * (b + c) = a * b + a * c.
 * </ul>
 * In other words, both addition and multiplication (without 0) are abelian groups.
 * 
 * @author kfuchsbe
 * @param <T> the type of elements of the field.
 * @see <a href="http://en.wikipedia.org/wiki/Field_(mathematics)">http://en.wikipedia.org/wiki/Field_(mathematics)</a>
 */
public interface Field<T> extends Ring<T> {

    @Override
    AbelianGroup<T> multiplicationStructure();

}
