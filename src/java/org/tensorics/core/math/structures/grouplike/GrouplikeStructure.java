/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.structures.Structure;

/**
 * Marks a algebraic structure that is of group-like type, aka it has one operation.
 * 
 * @author kfuchsbe
 * @param <T> the type of the element of the underlying set.
 */
public interface GrouplikeStructure<T> extends Structure<T> {

    /**
     * Has to return the binary operation (M x M -> M) for this Magma.
     * 
     * @return the binary operation.
     */
    BinaryOperation<T> operation();

}
