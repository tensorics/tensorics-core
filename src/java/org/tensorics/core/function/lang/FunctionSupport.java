/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.scalar.lang.ScalarSupport;

import com.google.common.base.Function;

public class FunctionSupport<Y> extends ScalarSupport<Y> {

    private final Environment<Y> environment;

    public FunctionSupport(Environment<Y> environment) {
        super(environment.field());
        this.environment = environment;
    }

    public <X> OngoingDiscreteFunctionConversionOperation<X, Y> withConversion(Function<X, Y> conversion) {
        return new OngoingDiscreteFunctionConversionOperation<>(environment, conversion);
    }
}
