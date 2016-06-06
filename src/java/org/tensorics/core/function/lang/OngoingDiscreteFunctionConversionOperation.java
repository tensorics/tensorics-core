/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;

import com.google.common.base.Function;

public class OngoingDiscreteFunctionConversionOperation<X, Y> {

    private Environment<Y> environment;
    private Function<X, Y> conversion;

    public OngoingDiscreteFunctionConversionOperation(Environment<Y> environment, Function<X, Y> conversion) {
        this.environment = environment;
        this.conversion = conversion;
    }

    public OngoingDiscreteFunctionOperation<X, Y> calculate(DiscreteFunction<X, Y> left) {
        return new OngoingDiscreteFunctionOperation<>(environment, conversion, left);
    }

}
