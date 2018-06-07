package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingQuantityBinaryOperationExpression<T, L extends Quantity<T>> {

    Expression<L> plus(L right) {
        return null;
    }

    Expression<L> plus(Expression<L> right) {
        return null;
    }

    Expression<L> minus(L right) {
        return null;
    }

    Expression<L> minus(Expression<L> right) {
        return null;
    }

    Expression<Quantity<T>> timesQ(Quantity<T> right) {
        return null;
    }

    Expression<Quantity<T>> timesQ(Expression<Quantity<T>> right) {
        return null;
    }

    Expression<L> times(T right) {
        return null;
    }

    Expression<L> times(Expression<T> right) {
        return null;
    }
    
    Expression<Quantity<T>> __(Quantity<T> right) {
        return timesQ(right);
    }

    Expression<Quantity<T>> __(Expression<Quantity<T>> right) {
        return timesQ(right);
    }

    
    Expression<Quantity<T>> dividedByQ(Quantity<T> right) {
        return null;
    }

    Expression<Quantity<T>> dividedByQ(Expression<Quantity<T>> right) {
        return null;
    }

    Expression<L> dividedBy(T right) {
        return null;
    }

    Expression<L> dividedBy(Expression<T> right) {
        return null;
    }

    Expression<Quantity<T>> per(Quantity<T> right) {
        return dividedByQ(right);
    }

    Expression<Quantity<T>> per(Expression<Quantity<T>> right) {
        return dividedByQ(right);
    }

    Expression<Quantity<T>> toThePowerOf(T right) {
        return null;
    }

    Expression<Quantity<T>> toThePowerOf(Expression<T> right) {
        return null;
    }

    Expression<Quantity<T>> toTheRootOf(T right) {
        return null;
    }

    Expression<Quantity<T>> toTheRootOf(Expression<T> right) {
        return null;
    }

}
