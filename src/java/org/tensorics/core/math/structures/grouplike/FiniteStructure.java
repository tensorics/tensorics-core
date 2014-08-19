/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

import java.util.Set;

import org.tensorics.core.math.structures.Structure;

/**
 * An algebraic structure, which has a finite number of elements.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements
 */
public interface FiniteStructure<T> extends Structure<T> {

    /**
     * Retrieves all the elements of the structure.
     * 
     * @return all the elements of the algebraic structure.
     */
    Set<T> elements();
}
