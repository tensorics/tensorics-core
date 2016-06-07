/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.MathFunctions;
import org.tensorics.core.iterable.lang.IterableOperationRepository;

import com.google.common.base.Function;

public class FunctionSupportWithConversion<X extends Comparable<? super X>, Y> {

    private Environment<Y> environment;
    private Function<X, Y> conversion;
    private IterableOperationRepository<Y> repository;

    public FunctionSupportWithConversion(Environment<Y> environment, Function<X, Y> conversion) {
        this.environment = environment;
        this.conversion = conversion;
        this.repository = new IterableOperationRepository<>(environment.field());
    }

    public OngoingDiscreteFunctionOperation<X, Y> calculate(DiscreteFunction<X, Y> left) {
        return new OngoingDiscreteFunctionOperation<>(environment, left, conversion);
    }

    public final Y averageOf(DiscreteFunction<X, Y> function) {
        return repository.average().perform(MathFunctions.yValuesOf(function));
    }

    public Y rmsOf(DiscreteFunction<X, Y> function) {
        return repository.rms().perform(MathFunctions.yValuesOf(function));
    }
}
