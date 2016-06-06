/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import org.tensorics.core.function.interpolation.InterpolationStrategy;

public class DefaultSingleTypedInterpolatedFunction<V extends Comparable<? super V>>
        extends DefaultInterpolatedFunction<V, V> implements SingleTypedInterpolatedFunction<V> {

    public DefaultSingleTypedInterpolatedFunction(DiscreteFunction<V, V> function,
            InterpolationStrategy<V, V> strategy) {
        super(function, strategy);
    }

}
