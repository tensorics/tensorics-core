package org.tensorics.incubate.quantities.any;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.expressions.UnresolvedBinaryOperation;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.operations.specific.Division;
import org.tensorics.core.math.operations.specific.Multiplication;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;
import org.tensorics.incubate.quantities.SiUnits;
import org.tensorics.incubate.quantities.expressions.ScaledQuantityExpression;

public class OngoingValueBinaryOperationExpression<T> {

    private final Expression<T> left;

    public OngoingValueBinaryOperationExpression(Expression<T> left) {
        this.left = requireNonNull(left, "left operand must not be null");
    }

    public Expression<Quantity<T>> times(Quantity<T> right) {
        return times(ResolvedExpression.of(right));
    }

    public Expression<Quantity<T>> times(Expression<Quantity<T>> right) {
        return binaryAnyOperationExpression(Multiplication.class, right);
    }

    public Expression<Quantity<T>> dividedBy(Quantity<T> right) {
        return dividedBy(ResolvedExpression.of(right));
    }

    public Expression<Quantity<T>> dividedBy(Expression<Quantity<T>> right) {
        return binaryAnyOperationExpression(Division.class, right);
    }

    public Expression<Quantity<T>> over(Quantity<T> right) {
        return dividedBy(right);
    }

    public Expression<Quantity<T>> over(Expression<Quantity<T>> right) {
        return dividedBy(right);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private BinaryOperationExpression<Quantity<T>> binaryAnyOperationExpression(
            Class<? extends BinaryOperation> operationType, Expression<Quantity<T>> right) {
        return new BinaryOperationExpression(UnresolvedBinaryOperation.of(operationType), oneTimes(left), right);
    }

    private Expression<Quantity<T>> oneTimes(Expression<T> any) {
        return new ScaledQuantityExpression<>(any, ResolvedExpression.of(SiUnits.one()));
    }

}
