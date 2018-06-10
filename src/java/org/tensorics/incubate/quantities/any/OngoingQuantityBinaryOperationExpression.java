package org.tensorics.incubate.quantities.any;

import static org.tensorics.incubate.quantities.Any.any;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingQuantityBinaryOperationExpression {

    public Expression<Quantity<Any>> plus(Quantity<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> plus(Expression<Quantity<Any>> right) {
        return null;
    }

    public Expression<Quantity<Any>> minus(Quantity<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> minus(Expression<Quantity<Any>> right) {
        return null;
    }

    public Expression<Quantity<Any>> timesQ(Quantity<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> timesQ(Expression<Quantity<Any>> right) {
        return null;
    }

    public Expression<Quantity<Any>> times(Any right) {
        return null;
    }

    public Expression<Quantity<Any>> times(Expression<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> dividedByQ(Quantity<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> dividedByQ(Expression<Quantity<Any>> right) {
        return null;
    }

    public Expression<Quantity<Any>> dividedBy(Any right) {
        return null;
    }

    public Expression<Quantity<Any>> dividedBy(Expression<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> per(Quantity<Any> right) {
        return dividedByQ(right);
    }

    public Expression<Quantity<Any>> per(Expression<Quantity<Any>> right) {
        return dividedByQ(right);
    }

    public Expression<Quantity<Any>> toThePowerOf(Any right) {
        return null;
    }

    public Expression<Quantity<Any>> toThePowerOf(String right) {
        return toThePowerOf(any(right));
    }

    public Expression<Quantity<Any>> toThePowerOf(Expression<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> toTheRootOf(Any right) {
        return null;
    }

    public Expression<Quantity<Any>> toTheRootOf(Expression<Any> right) {
        return null;
    }

}
