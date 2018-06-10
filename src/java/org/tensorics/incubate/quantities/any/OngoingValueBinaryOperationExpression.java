package org.tensorics.incubate.quantities.any;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;

public class OngoingValueBinaryOperationExpression {

    public Expression<Quantity<Any>> times(Quantity<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> times(Expression<Quantity<Any>> right) {
        return null;
    }

    public Expression<Quantity<Any>> dividedBy(Quantity<Any> right) {
        return null;
    }

    public Expression<Quantity<Any>> dividedBy(Expression<Quantity<Any>> right) {
        return null;
    }

    public Expression<Quantity<Any>> over(Quantity<Any> right) {
        return dividedBy(right);
    }

    public Expression<Quantity<Any>> over(Expression<Quantity<Any>> right) {
        return dividedBy(right);
    }

    public Expression<Quantity<Any>> toThePowerOf(Any right) {
        return null;
    }

    public Expression<Quantity<Any>> toThePowerOf(Expression<Any> right) {
        return null;
    }

}
