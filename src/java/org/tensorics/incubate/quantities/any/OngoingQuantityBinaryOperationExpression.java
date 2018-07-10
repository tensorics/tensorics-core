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
import org.tensorics.incubate.quantities.expressions.AnyExpression;
import org.tensorics.incubate.quantities.expressions.ScaledQuantityExpression;

public class OngoingQuantityBinaryOperationExpression<T> {

    private final Expression<Quantity<T>> left;

    public OngoingQuantityBinaryOperationExpression(Expression<Quantity<T>> left) {
        this.left = requireNonNull(left, "left operand must not be null");
    }

    public Expression<Quantity<T>> plus(Quantity<T> right) {
        return plus(ResolvedExpression.of(right));
    }

    public Expression<Quantity<T>> plus(Expression<Quantity<T>> right) {
        return binaryOperationExpression(Addition.class, right);
    }

    public Expression<Quantity<T>> minus(Quantity<T> right) {
        return minus(ResolvedExpression.of(right));
    }

    public Expression<Quantity<T>> minus(Expression<Quantity<T>> right) {
        return binaryOperationExpression(Subtraction.class, right);
    }

    public Expression<Quantity<T>> timesQ(Quantity<T> right) {
        return timesQ(ResolvedExpression.of(right));
    }

    public Expression<Quantity<T>> timesQ(Expression<Quantity<T>> right) {
        return binaryOperationExpression(Multiplication.class, right);
    }

    public Expression<Quantity<T>> dividedByQ(Quantity<T> right) {
        return dividedByQ(ResolvedExpression.of(right));
    }

    public Expression<Quantity<T>> dividedByQ(Expression<Quantity<T>> right) {
        return binaryOperationExpression(Division.class, right);
    }

    public Expression<Quantity<T>> per(Quantity<T> right) {
        return dividedByQ(right);
    }

    public Expression<Quantity<T>> per(Expression<Quantity<T>> right) {
        return dividedByQ(right);
    }

    /* special things, which involve quantities and ANY */

    public Expression<Quantity<T>> toThePowerOf(String right) {
        return toThePowerOf(any(right));
    }

    public Expression<Quantity<T>> toThePowerOf(Any right) {
        return toThePowerOf(ResolvedExpression.of(right));
    }

    public Expression<Quantity<T>> toThePowerOf(Expression<Any> right) {
        return binaryAnyOperationExpression(PowerOperation.class, new AnyExpression<>(right));
    }

    public Expression<Quantity<T>> toTheRootOf(String right) {
        return toTheRootOf(any(right));
    }

    public Expression<Quantity<T>> toTheRootOf(Any right) {
        return toTheRootOf(new AnyExpression<>(ResolvedExpression.of(right)));
    }

    public Expression<Quantity<T>> toTheRootOf(Expression<T> right) {
        return binaryAnyOperationExpression(RootOperation.class, right);
    }

    public Expression<Quantity<T>> times(String right) {
        return times(any(right));
    }

    public Expression<Quantity<T>> times(Any right) {
        return times(new AnyExpression<>(ResolvedExpression.of(right)));
    }

    public Expression<Quantity<T>> times(Expression<T> right) {
        return binaryAnyOperationExpression(Multiplication.class, right);
    }

    public Expression<Quantity<T>> dividedBy(String right) {
        return dividedBy(any(right));
    }

    public Expression<Quantity<T>> dividedBy(Any right) {
        return dividedBy(new AnyExpression<>(ResolvedExpression.of(right)));
    }

    public Expression<Quantity<T>> dividedBy(Expression<T> right) {
        return binaryAnyOperationExpression(Division.class, right);
    }

    @SuppressWarnings({ "rawtypes" })
    private BinaryOperationExpression<Quantity<T>> binaryAnyOperationExpression(
            Class<? extends BinaryOperation> operationType, Expression<T> right) {
        return binaryOperationExpression(operationType, oneTimes(right));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private BinaryOperationExpression<Quantity<T>> binaryOperationExpression(
            Class<? extends BinaryOperation> operationType, Expression<Quantity<T>> right) {
        return new BinaryOperationExpression(UnresolvedBinaryOperation.of(operationType), left, right);
    }

    private Expression<Quantity<T>> oneTimes(Expression<T> any) {
        return new ScaledQuantityExpression<>(any, ResolvedExpression.of(SiUnits.one()));
    }
}
