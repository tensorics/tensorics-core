/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.OperandPair;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.units.Unit;

import com.google.common.base.Optional;

/**
 * Base class for operations on physical quantities, that can perform sum or difference, depending on the scalar
 * operation that is passed into the constructor. The left and right operand will be converted to the same unit (if they
 * are not provided like this). The errors and validity flags will be propagated according to corresponding strategies
 * given in the environment.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which all the operations are based on
 */
public class QuantitySumOrDifferenceOperation<S> extends QuantityBinaryOperation<S> {

    public QuantitySumOrDifferenceOperation(QuantityEnvironment<S> environment, BinaryOperation<S> operation) {
        super(environment, operation);
    }

    @Override
    public QuantifiedValue<S> perform(QuantifiedValue<S> left, QuantifiedValue<S> right) {
        OperandPair<S, Unit> pair = environment().quantification().asSameUnit(left, right);
        Optional<S> error = environment().errorPropagation().errorForSumAndDifference(pair.left(), pair.right());
        S value = operation().perform(pair.left().value(), pair.right().value());
        boolean validity = validityFor(left, right);
        Unit unit = pair.unit();
        return ImmutableQuantifiedValue.of(value, unit).withError(error).withValidity(validity);
    }

}
