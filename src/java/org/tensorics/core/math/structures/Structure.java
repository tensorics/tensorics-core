/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures;

/**
 * The base interface for all algebraic structures. A basic property of an mathematical structure is always a set of
 * elements. Since there can be infinite and finite structures, it does not always make sense (or is impossible) to
 * explicitly state the involved set.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the mathematical structure
 * @see <a href="http://en.wikipedia.org/wiki/Algebraic_structure">http://en.wikipedia.org/wiki/Algebraic_structure</a>
 */
public interface Structure<T> {
    /* Only marker interface */
}
