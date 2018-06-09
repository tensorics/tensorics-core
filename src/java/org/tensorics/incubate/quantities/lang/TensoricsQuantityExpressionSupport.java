package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public interface TensoricsQuantityExpressionSupport<T> {

    OngoingValueBinaryOperationExpression<T> __(T left);

    OngoingValueBinaryOperationExpression<T> __(Expression<T> left);

    <L extends Quantity<T>> OngoingQuantityBinaryOperationExpression<T, L> _q(L left);

    <L extends Quantity<T>> OngoingQuantityBinaryOperationExpression<T, L> _q(Expression<L> left);

    <R extends Quantity<T>> OngoingTypedReturn<T, R> calc(Class<? super R> left);

}
