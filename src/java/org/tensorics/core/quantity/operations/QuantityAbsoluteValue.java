/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

public class QuantityAbsoluteValue<S> extends QuantityUnaryOperation<S> {

    public QuantityAbsoluteValue(QuantityEnvironment<S> environment) {
        super(environment);
    }

    @Override
    public QuantifiedValue<S> perform(QuantifiedValue<S> scalar) {
        S newValue = environment().field().absoluteValue().perform(scalar.value());
        return Tensorics.quantityOf(newValue, scalar.unit()).withError(scalar.error()).withValidity(scalar.validity());
    }

}
