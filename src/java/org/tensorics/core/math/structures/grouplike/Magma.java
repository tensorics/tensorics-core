/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

/**
 * The algebraic structure of a Magma (or also called groupoid). It has the following properties:
 * <ul>
 * <li>a set (M) of elements of type T
 * <li>one binary operation M x M -> M
 * </ul>
 * 
 * @param <T>
 * @see <a href="http://en.wikipedia.org/wiki/Magma_(algebra)">http://en.wikipedia.org/wiki/Magma_(algebra)</a>
 */
public interface Magma<T> extends GrouplikeStructure<T> {
    /* everything already defined in super interfaces */
}
