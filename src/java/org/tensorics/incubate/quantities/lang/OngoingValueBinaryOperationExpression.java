package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingValueBinaryOperationExpression<T> {

    public <R extends Quantity<T>> Expression<R> times(R right) {
        return null;
    }

    public <R extends Quantity<T>> Expression<R> times(Expression<R> right) {
        return null;
    }

    public <R extends Quantity<T>> Expression<R> __(R right) {
        return times(right);
    }

    public <R extends Quantity<T>> Expression<R> __(Expression<R> right) {
        return times(right);
    }

    public Expression<Quantity<T>> dividedBy(Quantity<T> right) {
        return null;
    }

    public Expression<Quantity<T>> dividedBy(Expression<Quantity<T>> right) {
        return null;
    }

    public Expression<Quantity<T>> per(Quantity<T> right) {
        return dividedBy(right);
    }

    public Expression<Quantity<T>> per(Expression<Quantity<T>> right) {
        return dividedBy(right);
    }

    public Expression<Quantity<T>> over(Quantity<T> right) {
        return dividedBy(right);
    }

    public Expression<Quantity<T>> over(Expression<Quantity<T>> right) {
        return dividedBy(right);
    }

    public Expression<Quantity<T>> toThePowerOf(T right) {
        return null;
    }

    public Expression<Quantity<T>> toThePowerOf(Expression<T> right) {
        return null;
    }

    public Expression<Quantity<T>> thRootOf(T right) {
        return null;
    }

    public Expression<Quantity<T>> thRootOf(Expression<T> right) {
        return null;
    }

}
