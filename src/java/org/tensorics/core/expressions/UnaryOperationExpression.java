package org.tensorics.core.expressions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;

import com.google.common.collect.ImmutableList;

/**
 * An unresolved expression, which can be resolved by a corresponding resolver by evaluating an unary operation. The
 * information it contains for the resolver, are the operation itself and an expression for the single operand on whose
 * resolved result the unary operation shall be performed.
 * 
 * @author kfuchsbe
 * @param <T> the type of the unary operand on which to perform the unary operation
 */
public class UnaryOperationExpression<T> extends AbstractDeferredExpression<T> {

    private final UnaryOperation<T> operation;
    private final Expression<T> operand;

    public UnaryOperationExpression(UnaryOperation<T> operation, Expression<T> operand) {
        super();
        this.operation = checkNotNull(operation, "Operation must not be null!");
        this.operand = checkNotNull(operand, "Operand must not be null!");
    }

    public UnaryOperation<T> getOperation() {
        return operation;
    }

    public Expression<T> getOperand() {
        return operand;
    }

    @Override
    public List<Node> getChildren() {
        return ImmutableList.<Node> of(operand);
    }

}
