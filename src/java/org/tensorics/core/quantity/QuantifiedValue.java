/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity;

/**
 * A scalar-type value representing a physical quantity. It contains:
 * <ul>
 * <li>the value itself
 * <li>the error of the value (optional)
 * <li>a validity flag
 * <li>the unit of the quantity
 * </ul>
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which the quantity shall be based on
 */
public interface QuantifiedValue<S> extends Quantified, ValidityAware, ErronousValue<S> {
    /*
     * NOTE: The logical name for this class would most probably be 'Quantity'. However, the intend to not use this name
     * at the present time is, to avoid name clashes with the jscience 'Quantity'.
     */
    /* Nothing special yet */
}
