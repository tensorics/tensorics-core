package org.tensorics.incubate.quantities.any;

import static java.util.Objects.requireNonNull;
import static org.tensorics.incubate.quantities.Any.any;

import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.expressions.UnresolvedBinaryOperation;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.operations.specific.Addition;
import org.tensorics.core.math.operations.specific.Division;
import org.tensorics.core.math.operations.specific.Multiplication;
import org.tensorics.core.math.operations.specific.PowerOperation;
import org.tensorics.core.math.operations.specific.RootOperation;
import org.tensorics.core.math.operations.specific.Subtraction;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Quantity;
import org.tensorics.incubate.quantities.SiUnits;
import org.tensorics.incubate.quantities.base.Dimensionless;
import org.tensorics.incubate.quantities.expressions.ScaledQuantityExpression;

public class OngoingQuantityBinaryOperationExpression {

    private final Expression<Quantity<Any>> left;

    public OngoingQuantityBinaryOperationExpression(Expression<Quantity<Any>> left) {
        this.left = requireNonNull(left, "left operand must not be null");
    }

    public Expression<Quantity<Any>> plus(Quantity<Any> right) {
        return plus(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> plus(Expression<Quantity<Any>> right) {
        return binaryOperationExpression(Addition.class, right);
    }

    public Expression<Quantity<Any>> minus(Quantity<Any> right) {
        return minus(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> minus(Expression<Quantity<Any>> right) {
        return binaryOperationExpression(Subtraction.class, right);
    }

    public Expression<Quantity<Any>> timesQ(Quantity<Any> right) {
        return timesQ(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> timesQ(Expression<Quantity<Any>> right) {
        return binaryOperationExpression(Multiplication.class, right);
    }

    public Expression<Quantity<Any>> dividedByQ(Quantity<Any> right) {
        return dividedByQ(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> dividedByQ(Expression<Quantity<Any>> right) {
        return binaryOperationExpression(Division.class, right);
    }

    public Expression<Quantity<Any>> per(Quantity<Any> right) {
        return dividedByQ(right);
    }

    public Expression<Quantity<Any>> per(Expression<Quantity<Any>> right) {
        return dividedByQ(right);
    }

    /* special things, which involve quantities and ANY */

    public Expression<Quantity<Any>> toThePowerOf(String right) {
        return toThePowerOf(any(right));
    }

    public Expression<Quantity<Any>> toThePowerOf(Any right) {
        return toThePowerOf(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> toThePowerOf(Expression<Any> right) {
        return binaryAnyOperationExpression(PowerOperation.class, right);
    }

    public Expression<Quantity<Any>> toTheRootOf(String right) {
        return toTheRootOf(any(right));
    }

    public Expression<Quantity<Any>> toTheRootOf(Any right) {
        return toTheRootOf(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> toTheRootOf(Expression<Any> right) {
        return binaryAnyOperationExpression(RootOperation.class, right);
    }

    public Expression<Quantity<Any>> times(String right) {
        return times(any(right));
    }

    public Expression<Quantity<Any>> times(Any right) {
        return times(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> times(Expression<Any> right) {
        return binaryAnyOperationExpression(Multiplication.class, right);
    }

    public Expression<Quantity<Any>> dividedBy(String right) {
        return dividedBy(any(right));
    }

    public Expression<Quantity<Any>> dividedBy(Any right) {
        return dividedBy(ResolvedExpression.of(right));
    }

    public Expression<Quantity<Any>> dividedBy(Expression<Any> right) {
        return binaryAnyOperationExpression(Division.class, right);
    }

    @SuppressWarnings({ "rawtypes" })
    private BinaryOperationExpression<Quantity<Any>> binaryAnyOperationExpression(
            Class<? extends BinaryOperation> operationType, Expression<Any> right) {
        return binaryOperationExpression(operationType, oneTimes(right));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private BinaryOperationExpression<Quantity<Any>> binaryOperationExpression(
            Class<? extends BinaryOperation> operationType, Expression<Quantity<Any>> right) {
        return new BinaryOperationExpression(UnresolvedBinaryOperation.of(operationType), left, right);
    }

    private Expression<Quantity<Any>> oneTimes(Expression<Any> any) {
        return new ScaledQuantityExpression<>(any, ResolvedExpression.of(SiUnits.one()));
    }
}
