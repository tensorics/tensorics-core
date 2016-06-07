/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.expressions;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.tree.domain.Expression;

public class FunctionToIterableExpression<T>
        extends ConversionOperationExpression<DiscreteFunction<?, T>, Iterable<T>> {

    public FunctionToIterableExpression(Conversion<DiscreteFunction<?, T>, Iterable<T>> operation,
            Expression<DiscreteFunction<?, T>> source) {
        super(operation, source);
    }

}
