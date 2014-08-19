/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.options;

import org.tensorics.core.quantity.ValidityAware;

/**
 * The validation strategy, which requires both operands to be valid, in order to have a valid result.
 * 
 * @author kfuchsbe
 */
public class RequireBothValidStrategy implements BinaryOperationValidityStrategy {

    @Override
    public boolean validityForBinaryOperation(ValidityAware left, ValidityAware right) {
        return (left.validity() && right.validity());
    }

    @Override
    public Class<BinaryOperationValidityStrategy> getMarkerInterface() {
        return BinaryOperationValidityStrategy.class;
    }

}
