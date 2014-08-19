/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.options;

import org.tensorics.core.quantity.ValidityAware;

/**
 * This strategy throws an exception, if at least one of the two operands is invalid.
 * 
 * @author kfuchsbe
 */
public class ThrowIfOneInvalidStrategy implements BinaryOperationValidityStrategy {

    @Override
    public boolean validityForBinaryOperation(ValidityAware left, ValidityAware right) {
        if (left.validity() && right.validity()) {
            return true;
        }
        throw new RuntimeException("One of the two operands is invalid!");
    }

    @Override
    public Class<BinaryOperationValidityStrategy> getMarkerInterface() {
        return BinaryOperationValidityStrategy.class;
    }

}
