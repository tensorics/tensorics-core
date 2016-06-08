/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;

import com.google.common.base.Function;
import com.google.common.base.Functions;

public class FunctionExpressionSupport<Y> extends FunctionExpressionSupportWithConversion<Y, Y> {

    FunctionExpressionSupport(Environment<Y> environment) {
        super(environment, Functions.identity());
    }

    public <X> FunctionExpressionSupportWithConversion<X, Y> withConversion(Function<X, Y> conversion) {
        return new FunctionExpressionSupportWithConversion<>(environment(), conversion);
    }
}
