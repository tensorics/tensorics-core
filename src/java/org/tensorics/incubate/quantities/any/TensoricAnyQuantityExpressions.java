package org.tensorics.incubate.quantities.any;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;

public class TensoricAnyQuantityExpressions {

    private static final TensoricsAnyQuantityExpressionSupport SUPPORT = new TensoricsAnyQuantityExpressionSupportImpl();

    public static final <T> OngoingQuantityBinaryOperationExpression<T> _q(Quantity<T> left) {
        return SUPPORT._q(left);
    }

    public static final <T> OngoingQuantityBinaryOperationExpression<T> _q(Expression<Quantity<T>> left) {
        return SUPPORT._q(left);
    }

    public static final <T> OngoingValueBinaryOperationExpression<T> _v(T left) {
        return SUPPORT._v(left);
    }

    public static final <T> OngoingValueBinaryOperationExpression<T> _v(Any left) {
        return SUPPORT._v(left);
    }

    public static final <T> OngoingValueBinaryOperationExpression<T> _v(Expression<T> left) {
        return SUPPORT._v(left);
    }

    public static final <T> OngoingValueBinaryOperationExpression<T> _v(String left) {
        return SUPPORT._v(left);
    }

}
