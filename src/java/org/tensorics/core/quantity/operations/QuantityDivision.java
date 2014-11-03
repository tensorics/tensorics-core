/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.units.Unit;

/**
 * The binary operation which describes the division of two quantities.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values (field elements) on which all the operations are based on
 */
public class QuantityDivision<S> extends QuantityBinaryOperation<S> {

    public QuantityDivision(QuantityEnvironment<S> environment) {
        super(environment, environment.field().division());
    }

    @Override
    public QuantifiedValue<S> perform(QuantifiedValue<S> left, QuantifiedValue<S> right) {
        S value = operation().perform(left.value(), right.value());
        Unit unit = environment().quantification().divide(left.unit(), right.unit());
        return Tensorics.quantityOf(value, unit).withValidity(validityFor(left, right))
                .withError(productError(left, right));
    }

}
