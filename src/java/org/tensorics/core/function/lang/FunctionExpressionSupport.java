/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.operations.Conversions;
import org.tensorics.core.commons.options.Environment;

public class FunctionExpressionSupport<Y> extends FunctionExpressionSupportWithConversion<Y, Y> {

    FunctionExpressionSupport(Environment<Y> environment) {
        super(environment, Conversions.identity());
    }

    public <X> FunctionExpressionSupportWithConversion<X, Y> withConversion(Conversion<X, Y> conversion) {
        return new FunctionExpressionSupportWithConversion<>(environment(), conversion);
    }
}
