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

public class OngoingValueBinaryOperationExpression {

    private final Expression<Any> left;

    public OngoingValueBinaryOperationExpression(Expression<Any> left) {
        this.left = requireNonNull(left, "left operand must not be null");
    }

    public Expression<Quantity<Any>> times(Quantity<Any> right) {
        return times(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> times(Expression<Quantity<Any>> right) {
        return binaryAnyOperationExpression(Multiplication.class, right);
    }

    public Expression<Quantity<Any>> dividedBy(Quantity<Any> right) {
        return dividedBy(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> dividedBy(Expression<Quantity<Any>> right) {
        return binaryAnyOperationExpression(Division.class, right);
    }

    public Expression<Quantity<Any>> over(Quantity<Any> right) {
        return dividedBy(right);
    }

    public Expression<Quantity<Any>> over(Expression<Quantity<Any>> right) {
        return dividedBy(right);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private BinaryOperationExpression<Quantity<Any>> binaryAnyOperationExpression(
            Class<? extends BinaryOperation> operationType, Expression<Quantity<Any>> right) {
        return new BinaryOperationExpression(UnresolvedBinaryOperation.of(operationType), oneTimes(left), right);
    }

    private Expression<Quantity<Any>> oneTimes(Expression<Any> any) {
        return new ScaledQuantityExpression<>(any, ResolvedExpression.of(SiUnits.one()));
    }

}
