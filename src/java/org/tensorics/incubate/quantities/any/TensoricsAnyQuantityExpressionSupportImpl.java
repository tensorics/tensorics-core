/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.any;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;

public class TensoricsAnyQuantityExpressionSupportImpl implements TensoricsAnyQuantityExpressionSupport {

    @Override
    public OngoingValueBinaryOperationExpression _v(Any left) {
        return new OngoingValueBinaryOperationExpression(ResolvedExpression.of(left));
    }

    @Override
    public OngoingValueBinaryOperationExpression _v(Expression<Any> left) {
        return new OngoingValueBinaryOperationExpression(left);
    }

    @Override
    public OngoingQuantityBinaryOperationExpression _q(Quantity<Any> left) {
        return new OngoingQuantityBinaryOperationExpression(ResolvedExpression.of(left));
    }

    @Override
    public OngoingQuantityBinaryOperationExpression _q(Expression<Quantity<Any>> left) {
        return new OngoingQuantityBinaryOperationExpression(left);
    }

}
