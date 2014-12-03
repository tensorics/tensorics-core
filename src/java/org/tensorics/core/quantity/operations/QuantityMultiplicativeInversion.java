/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantificationStrategy;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.units.Unit;

/**
 * An unary operation describing the multiplicative inversion of a physical quantity (aka '1/x').
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which all the operations are based
 */
public class QuantityMultiplicativeInversion<S> extends QuantityUnaryOperation<S> {

    public QuantityMultiplicativeInversion(QuantityEnvironment<S> environment) {
        super(environment);
    }

    @Override
    public QuantifiedValue<S> perform(QuantifiedValue<S> scalar) {
        S newValue = environment().field().multiplicativeInversion().perform(scalar.value());
        QuantificationStrategy<S> quant = environment().quantification();
        Unit newUnit = quant.divide(quant.one(), scalar.unit());
        /* XXX is the error propagated correctly here? */
        return Tensorics.quantityOf(newValue, newUnit).withError(scalar.error()).withValidity(scalar.validity());
    }

}
