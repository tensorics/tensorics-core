package org.tensorics.incubate.quantities.any;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;

public interface TensoricsAnyQuantityExpressionSupport {

    <T> OngoingValueBinaryOperationExpression<T> _v(String left);

    <T> OngoingValueBinaryOperationExpression<T> _v(Any left);

    <T> OngoingValueBinaryOperationExpression<T> _v(T left);

    <T> OngoingValueBinaryOperationExpression<T> _v(Expression<T> left);

    <T> OngoingQuantityBinaryOperationExpression<T> _q(Quantity<T> left);

    <T> OngoingQuantityBinaryOperationExpression<T> _q(Expression<Quantity<T>> left);

}
