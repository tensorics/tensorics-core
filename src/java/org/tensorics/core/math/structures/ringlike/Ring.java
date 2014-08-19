/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.ringlike;

import org.tensorics.core.math.structures.grouplike.AbelianGroup;

/**
 * A ringlike algebraic structure, whose additive monoid is an abelian group.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the underlying set
 */
public interface Ring<T> extends Semiring<T> {

    @Override
    AbelianGroup<T> additionStructure();
}
