/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.expressions;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.tree.domain.Expression;

public class FunctionToIterableExpression<X, Y>
        extends ConversionOperationExpression<DiscreteFunction<X, Y>, Iterable<Y>> {

    public FunctionToIterableExpression(Conversion<DiscreteFunction<X, Y>, Iterable<Y>> operation,
            Expression<DiscreteFunction<X, Y>> source) {
        super(operation, source);
    }

}
