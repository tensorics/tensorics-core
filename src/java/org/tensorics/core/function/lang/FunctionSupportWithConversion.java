/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import static org.tensorics.core.function.MathFunctions.yValuesOf;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.iterable.lang.ScalarIterableSupport;

import com.google.common.base.Function;

public class FunctionSupportWithConversion<X, Y> extends ScalarIterableSupport<Y> {

    private Environment<Y> environment;
    private Function<X, Y> conversion;

    public FunctionSupportWithConversion(Environment<Y> environment, Function<X, Y> conversion) {
        super(environment.field());
        this.environment = environment;
        this.conversion = conversion;
    }

    // XXX: check that the cast is OK, otherwise throw an exception
    @SuppressWarnings("unchecked")
    public <Z extends Comparable<? super Z>> OngoingDiscreteFunctionOperation<Z, Y> calculate(
            DiscreteFunction<Z, Y> left) {
        return new OngoingDiscreteFunctionOperation<>(environment, left, (Function<Z, Y>) conversion);
    }

    public final Y averageOf(DiscreteFunction<X, Y> function) {
        return avarageOf(yValuesOf(function));
    }

    public Y rmsOf(DiscreteFunction<X, Y> function) {
        return rmsOf(yValuesOf(function));
    }

    protected Environment<Y> environment() {
        return environment;
    }
}
