/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

/**
 * The algebraic structure of an abelian group (or commutative group). Thus the group operation is commutative and
 * associative.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the underlying set
 */
public interface AbelianGroup<T> extends CommutativeMonoid<T>, Group<T> {
    /* All methods correctly defined in super interfaces */
}
