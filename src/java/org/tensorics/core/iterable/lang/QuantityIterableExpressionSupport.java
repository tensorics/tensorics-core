/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.lang;

import org.tensorics.core.quantity.lang.QuantityExpressionSupport;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * Provides methods, being part of the tensorics eDSL, which deal with expressions of iterables of quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which all the operations are based on.
 */
public class QuantityIterableExpressionSupport<V> extends QuantityExpressionSupport<V> {

    protected QuantityIterableExpressionSupport(QuantityEnvironment<V> environment) {
        super(environment);
    }

    /* Nothing implemented for the moment */
}
