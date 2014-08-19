/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A base class for binary operations on physical quantities. It keeps the scalar bianary operation to be retrieved by
 * the sub classes.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values on which all operations are based on (elements of a field)
 */
public abstract class QuantityBinaryOperation<V> extends AbstractQuantityOperation<V> implements
        BinaryOperation<QuantifiedValue<V>> {

    private final BinaryOperation<V> scalarBinaryOeration;

    protected QuantityBinaryOperation(QuantityEnvironment<V> environment, BinaryOperation<V> scalarBinaryOperation) {
        super(environment);
        this.scalarBinaryOeration = scalarBinaryOperation;
    }

    protected BinaryOperation<V> operation() {
        return scalarBinaryOeration;
    }

}
