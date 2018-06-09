package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingTypedValueBinaryOperationExpression<T, R extends Quantity<T>> {

    public Expression<R> times(R right) {
        return null;
    }

    public Expression<R> times(Expression<R> right) {
        return null;
    }

    public Expression<R> __(R right) {
        return times(right);
    }

    public Expression<R> __(Expression<R> right) {
        return times(right);
    }

    public Expression<R> dividedBy(Quantity<T> right) {
        return null;
    }

    public Expression<R> dividedBy(Expression<Quantity<T>> right) {
        return null;
    }

    public Expression<R> per(Quantity<T> right) {
        return dividedBy(right);
    }

    public Expression<R> per(Expression<Quantity<T>> right) {
        return dividedBy(right);
    }

    public Expression<R> over(Quantity<T> right) {
        return dividedBy(right);
    }

    public Expression<R> over(Expression<Quantity<T>> right) {
        return dividedBy(right);
    }

}
