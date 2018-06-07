package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingTypedQuantityBinaryOperationExpression<T, R extends Quantity<T>> {

    Expression<R> times(Quantity<T> right) {
        return null;
    }

    Expression<R> times(Expression<Quantity<T>> right) {
        return null;
    }

    Expression<R> __(Quantity<T> right) {
        return times(right);
    }

    Expression<R> __(Expression<Quantity<T>> right) {
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
    
    Expression<R> toThePowerOf(T right) {
        return null;
    }

    Expression<R> toThePowerOf(Expression<T> right) {
        return null;
    }

    Expression<R> toTheRootOf(T right) {
        return null;
    }

    Expression<R> toTheRootOf(Expression<T> right) {
        return null;
    }

}
