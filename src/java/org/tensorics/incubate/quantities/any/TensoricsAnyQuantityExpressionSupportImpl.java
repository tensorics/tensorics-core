/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.any;

import static org.tensorics.incubate.quantities.Any.any;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;
import org.tensorics.incubate.quantities.expressions.AnyExpression;

public class TensoricsAnyQuantityExpressionSupportImpl implements TensoricsAnyQuantityExpressionSupport {

    @Override
    public <T> OngoingValueBinaryOperationExpression<T> _v(String left) {
        return _v(any(left));
    }

    @Override
    public <T> OngoingValueBinaryOperationExpression<T> _v(Any left) {
        return _v(new AnyExpression<>(ResolvedExpression.of(left)));
    }

    @Override
    public <T> OngoingValueBinaryOperationExpression<T> _v(T left) {
        return _v(ResolvedExpression.of(left));
    }

    @Override
    public <T> OngoingValueBinaryOperationExpression<T> _v(Expression<T> left) {
        return new OngoingValueBinaryOperationExpression<>(left);
    }

    @Override
    public <T> OngoingQuantityBinaryOperationExpression<T> _q(Quantity<T> left) {
        return new OngoingQuantityBinaryOperationExpression<>(ResolvedExpression.of(left));
    }

    @Override
    public <T> OngoingQuantityBinaryOperationExpression<T> _q(Expression<Quantity<T>> left) {
        return new OngoingQuantityBinaryOperationExpression<>(left);
    }

}
