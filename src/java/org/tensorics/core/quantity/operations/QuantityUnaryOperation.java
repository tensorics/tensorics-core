/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A base class for unary operations on physical quantities.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values (field elements) on which all the operations are based on
 */
public abstract class QuantityUnaryOperation<S> extends AbstractQuantityOperation<S> implements
        UnaryOperation<QuantifiedValue<S>> {

    protected QuantityUnaryOperation(QuantityEnvironment<S> environment) {
        super(environment);
    }

}
