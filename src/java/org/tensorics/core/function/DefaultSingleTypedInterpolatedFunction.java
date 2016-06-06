/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import org.tensorics.core.function.interpolation.InterpolationStrategy;

import com.google.common.base.Function;

public class DefaultSingleTypedInterpolatedFunction<V extends Comparable<? super V>>
        extends DefaultInterpolatedFunction<V, V> implements SingleTypedInterpolatedFunction<V> {

    public DefaultSingleTypedInterpolatedFunction(DiscreteFunction<V, V> function, InterpolationStrategy<V> strategy) {
        super(function, strategy, new Function<V, V>() {

            @Override
            public V apply(V input) {
                return input;
            }
        });
    }

    private DefaultSingleTypedInterpolatedFunction(DiscreteFunction<V, V> function, InterpolationStrategy<V> strategy,
            Function<V, V> conversion) {
        super(function, strategy, conversion);
    }

}
