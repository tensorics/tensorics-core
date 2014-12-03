/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A binary operation describing the multiplication of two physical quantities.
 * 
 * @author kfuchsbe
 * @param <S> the type of scalars (field elements) on which all the operations are based on
 */
public class QuantityMultiplication<S> extends QuantityBinaryOperation<S> {

    public QuantityMultiplication(QuantityEnvironment<S> environment) {
        super(environment, environment.field().multiplication());
    }

    @Override
    public QuantifiedValue<S> perform(QuantifiedValue<S> left, QuantifiedValue<S> right) {
        S value = operation().perform(left.value(), right.value());
        org.tensorics.core.units.Unit unit = environment().quantification().multiply(left.unit(), right.unit());
        return Tensorics.quantityOf(value, unit).withValidity(validityFor(left, right))
                .withError(productError(left, right));
    }

}
