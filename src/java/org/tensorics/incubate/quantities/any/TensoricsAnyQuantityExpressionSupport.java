package org.tensorics.incubate.quantities.any;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;

public interface TensoricsAnyQuantityExpressionSupport {

    OngoingValueBinaryOperationExpression _v(Any left);

    OngoingValueBinaryOperationExpression _v(Expression<Any> left);

    OngoingQuantityBinaryOperationExpression _q(Quantity<Any> left);

    OngoingQuantityBinaryOperationExpression _q(Expression<Quantity<Any>> left);

}
