/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.operations;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.lang.FunctionSupportWithConversion;

import com.google.common.base.Function;

public class DiscreteFunctionSubtraction<X extends Comparable<? super X>, Y> extends FunctionSupportWithConversion<X, Y>
        implements DiscreteFunctionBinaryOperation<X, Y> {

    public DiscreteFunctionSubtraction(Environment<Y> environment, Function<X, Y> conversion) {
        super(environment, conversion);
    }

    @Override
    public DiscreteFunction<X, Y> perform(DiscreteFunction<X, Y> left, DiscreteFunction<X, Y> right) {
        return calculate(left).minus(right);
    }
}
