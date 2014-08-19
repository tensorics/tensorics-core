/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * The operation which describes the subtraction of two pysical quantities
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which quantities are based on
 */
public class QuantitySubtraction<S> extends QuantitySumOrDifferenceOperation<S> {

    public QuantitySubtraction(QuantityEnvironment<S> environment) {
        super(environment, environment.field().subtraction());
    }

}
