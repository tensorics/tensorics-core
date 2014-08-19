/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.options;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.quantity.ValidityAware;

/**
 * Instances of this interface define, how the validity values for binary operations of two validity aware values are
 * propageted.
 * 
 * @author kfuchsbe
 */
public interface BinaryOperationValidityStrategy extends ManipulationOption {

    /**
     * Has to return the resulting validity for the combination of the two scalars.
     * 
     * @param left the left operand of a binary operation
     * @param right the right operand of a binary operation
     * @return the combined validity
     * @throws RuntimeException if the combination of the two is not allowed
     */
    boolean validityForBinaryOperation(ValidityAware left, ValidityAware right);

}
