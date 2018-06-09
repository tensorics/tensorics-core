package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;

public class TensoricAnyQuantityExpressions {

    private static final TensoricsQuantityExpressionSupport<Any> SUPPORT = null;

    public static final <L extends Quantity<Any>> OngoingQuantityBinaryOperationExpression<Any, L> _q(L left) {
        return SUPPORT._q(left);
    }

    public static final <L extends Quantity<Any>> OngoingQuantityBinaryOperationExpression<Any, L> _q(
            Expression<L> left) {
        return SUPPORT._q(left);
    }

    public static final OngoingValueBinaryOperationExpression<Any> _v(Any left) {
        return SUPPORT._v(left);
    }

    public static final OngoingValueBinaryOperationExpression<Any> _v(Expression<Any> left) {
        return SUPPORT._v(left);
    }

    public static final OngoingValueBinaryOperationExpression<Any> _v(String left) {
        return SUPPORT._v(Any.any(left));
    }

}
