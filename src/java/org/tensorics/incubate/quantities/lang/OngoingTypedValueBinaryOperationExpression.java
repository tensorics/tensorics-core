package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingTypedValueBinaryOperationExpression<T, R extends Quantity<T>> {

    Expression<R> times(R right) {
        return null;
    }

    Expression<R> times(Expression<R> right) {
        return null;
    }

    Expression<R> __(R right) {
        return times(right);
    }

    Expression<R> __(Expression<R> right) {
        return times(right);
    }

    Expression<R> dividedBy(Quantity<T> right) {
        return null;
    }

    Expression<R> dividedBy(Expression<Quantity<T>> right) {
        return null;
    }

    Expression<R> per(Quantity<T> right) {
        return dividedBy(right);
    }

    Expression<R> per(Expression<Quantity<T>> right) {
        return dividedBy(right);
    }

    Expression<R> over(Quantity<T> right) {
        return dividedBy(right);
    }

    Expression<R> over(Expression<Quantity<T>> right) {
        return dividedBy(right);
    }

}
