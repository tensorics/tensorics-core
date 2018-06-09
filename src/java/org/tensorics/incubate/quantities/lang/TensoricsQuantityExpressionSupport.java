package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public interface TensoricsQuantityExpressionSupport<T> {

    OngoingValueBinaryOperationExpression<T> _v(T left);

    OngoingValueBinaryOperationExpression<T> _v(Expression<T> left);

    <L extends Quantity<T>> OngoingQuantityBinaryOperationExpression<T, L> _q(L left);

    <L extends Quantity<T>> OngoingQuantityBinaryOperationExpression<T, L> _q(Expression<L> left);


}
