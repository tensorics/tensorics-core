/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.ringlike;

import org.tensorics.core.math.structures.Structure;
import org.tensorics.core.math.structures.grouplike.GrouplikeStructure;

/**
 * An algebraic structure, which is of ringlike type, aka it has two operations. Typically, they are called addition (+)
 * and multiplication (*). Together with the underlying set of elements, these operations form different grouplike
 * structures themselves.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the underlying set.
 */
public interface RinglikeStructure<T> extends Structure<T> {

    /**
     * Returns the grouplike structure representing the addition of elements.
     * 
     * @return the group like structure for the addition
     */
    GrouplikeStructure<T> additionStructure();

    /**
     * Returns the grouplike structure representing the multiplication of elements.
     * 
     * @return the group like structure for multiplication
     */
    GrouplikeStructure<T> multiplicationStructure();

}
