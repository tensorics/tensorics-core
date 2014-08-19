/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * The binary operation which describes the addition of two quantities.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (elements of the field on which the operations are based on)
 */
public class QuantityAddition<S> extends QuantitySumOrDifferenceOperation<S> {

    public QuantityAddition(QuantityEnvironment<S> environment) {
        super(environment, environment.field().addition());
    }

}
