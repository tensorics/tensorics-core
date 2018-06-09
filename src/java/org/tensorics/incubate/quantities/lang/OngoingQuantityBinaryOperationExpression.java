package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingQuantityBinaryOperationExpression<T, L extends Quantity<T>> {

    public Expression<L> plus(L right) {
        return null;
    }

    public Expression<L> plus(Expression<L> right) {
        return null;
    }

    public Expression<L> minus(L right) {
        return null;
    }

    public Expression<L> minus(Expression<L> right) {
        return null;
    }

    public Expression<Quantity<T>> timesQ(Quantity<T> right) {
        return null;
    }

    public Expression<Quantity<T>> timesQ(Expression<Quantity<T>> right) {
        return null;
    }

    public Expression<L> times(T right) {
        return null;
    }

    public Expression<L> times(Expression<T> right) {
        return null;
    }

    public Expression<Quantity<T>> __(Quantity<T> right) {
        return timesQ(right);
    }

    public Expression<Quantity<T>> __(Expression<Quantity<T>> right) {
        return timesQ(right);
    }

    public Expression<Quantity<T>> dividedByQ(Quantity<T> right) {
        return null;
    }

    public Expression<Quantity<T>> dividedByQ(Expression<Quantity<T>> right) {
        return null;
    }

    public Expression<L> dividedBy(T right) {
        return null;
    }

    public Expression<L> dividedBy(Expression<T> right) {
        return null;
    }

    public Expression<Quantity<T>> per(Quantity<T> right) {
        return dividedByQ(right);
    }

    public Expression<Quantity<T>> per(Expression<Quantity<T>> right) {
        return dividedByQ(right);
    }

    public Expression<Quantity<T>> toThePowerOf(T right) {
        return null;
    }

    public Expression<Quantity<T>> toThePowerOf(Expression<T> right) {
        return null;
    }

    public Expression<Quantity<T>> toTheRootOf(T right) {
        return null;
    }

    public Expression<Quantity<T>> toTheRootOf(Expression<T> right) {
        return null;
    }

}
