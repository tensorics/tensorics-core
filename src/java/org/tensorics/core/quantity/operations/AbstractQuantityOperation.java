/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.BinaryOperationValidityStrategy;
import org.tensorics.core.quantity.options.QuantityEnvironment;

import com.google.common.base.Optional;

/**
 * @author kfuchsbe
 * @param <V>
 */
public class AbstractQuantityOperation<V> {

    private final QuantityEnvironment<V> mathsEnvironment;

    protected AbstractQuantityOperation(QuantityEnvironment<V> environment) {
        super();
        this.mathsEnvironment = environment;
    }

    protected QuantityEnvironment<V> environment() {
        return mathsEnvironment;
    }

    protected boolean validityFor(QuantifiedValue<V> leftOperand, QuantifiedValue<V> rightOperand) {
        return environment().options().get(BinaryOperationValidityStrategy.class)
                .validityForBinaryOperation(leftOperand, rightOperand);
    }

    protected Optional<V> productError(QuantifiedValue<V> leftOperand, QuantifiedValue<V> rightOperand) {
        return environment().errorPropagation().errorForProductAndDivision(leftOperand, rightOperand);
    }

}