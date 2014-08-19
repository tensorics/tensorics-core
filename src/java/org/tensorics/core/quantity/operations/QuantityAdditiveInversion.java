/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * The unary operation which which calculates the additive inversion of a quantity (aka 'the negative'). Error, validity
 * and the unit of the result are the same as the input quantity, only the value itself will be inverted.
 * 
 * @author kfuchsbe
 * @param <S> the type of scalars on which all the operations are based (elements of a field)
 */
public class QuantityAdditiveInversion<S> extends QuantityUnaryOperation<S> {

    public QuantityAdditiveInversion(QuantityEnvironment<S> environment) {
        super(environment);
    }

    @Override
    public QuantifiedValue<S> perform(QuantifiedValue<S> scalar) {
        S newValue = environment().field().additiveInversion().perform(scalar.value());
        return ImmutableQuantifiedValue.of(newValue, scalar.unit()).withError(scalar.error())
                .withValidity(scalar.validity());
    }

}
