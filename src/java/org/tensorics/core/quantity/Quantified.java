/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity;

import org.tensorics.core.units.Unit;

/**
 * Any object that has a unit.
 * 
 * @author kfuchsbe
 */
public interface Quantified {

    /**
     * Retrieves the unit of the entity
     * 
     * @return the unit of the quantified entity.
     */
    Unit unit();
}
