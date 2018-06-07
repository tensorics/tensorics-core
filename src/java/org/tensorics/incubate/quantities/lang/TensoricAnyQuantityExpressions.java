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

    public static final <R extends Quantity<Any>> OngoingTypedReturn<Any, R> calc(Class<R> left) {
        return SUPPORT.calc(left);
    };
    
    
    public static final OngoingValueBinaryOperationExpression<Any> __(Any left) {
        return SUPPORT.__(left);
    }

    public static final OngoingValueBinaryOperationExpression<Any> __(Expression<Any> left) {
        return SUPPORT.__(left);
    }

    public static final OngoingValueBinaryOperationExpression<Any> __(String left) {
        return SUPPORT.__(Any.any(left));
    }

}
