/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.incubate.quantities.Quantity;

public class NewFieldBasedQuantitySupportImpl<T> implements NewQuantitySupport<T> {

    private final QuantityEnvironment<T> env;

    public NewFieldBasedQuantitySupportImpl(QuantityEnvironment<T> env) {
        this.env = env;
    }

    @Override
    public <Q extends Quantity<T>> Q q(T value, Q unit) {
        // TODO Auto-generated method stub
        return null;
    }

}
