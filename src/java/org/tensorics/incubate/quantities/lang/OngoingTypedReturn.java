package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingTypedReturn<T, R extends Quantity<T>> {

    OngoingTypedQuantityBinaryOperationExpression<T, R> asQ(Quantity<T> left) {
        return null;
    }

    OngoingTypedQuantityBinaryOperationExpression<T, R> asQ(Expression<Quantity<T>> left) {
        return null;
    }

    OngoingTypedValueBinaryOperationExpression<T, R> as(T left) {
        return null;
    }

    OngoingTypedValueBinaryOperationExpression<T, R> as(Expression<T> left) {
        return null;
    }

}
