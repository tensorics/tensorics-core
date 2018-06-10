package org.tensorics.incubate.quantities.any;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;

public class TensoricAnyQuantityExpressions {

    private static final TensoricsAnyQuantityExpressionSupport SUPPORT = new TensoricsAnyQuantityExpressionSupportImpl();

    public static final OngoingQuantityBinaryOperationExpression _q(Quantity<Any> left) {
        return SUPPORT._q(left);
    }

    public static final OngoingQuantityBinaryOperationExpression _q(Expression<Quantity<Any>> left) {
        return SUPPORT._q(left);
    }

    public static final OngoingValueBinaryOperationExpression _v(Any left) {
        return SUPPORT._v(left);
    }

    public static final OngoingValueBinaryOperationExpression _v(Expression<Any> left) {
        return SUPPORT._v(left);
    }

    public static final OngoingValueBinaryOperationExpression _v(String left) {
        return SUPPORT._v(Any.any(left));
    }

}
