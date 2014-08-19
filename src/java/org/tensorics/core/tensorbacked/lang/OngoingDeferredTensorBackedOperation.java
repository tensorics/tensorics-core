/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.lang;

import static org.tensorics.core.tensorbacked.expressions.TensorbackedExpressions.extracted;
import static org.tensorics.core.tensorbacked.expressions.TensorbackedExpressions.wrapped;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.expressions.TensorExpressions;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Part of the tensorics fluent API that provides methods to describe the right hand part of binary expressions on
 * tensor backed objects.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values (elements of the fields on which all the operations are based on)
 * @param <TB> the type of the tensor backed objects
 */
public class OngoingDeferredTensorBackedOperation<V, TB extends Tensorbacked<V>> {

    private final Environment<V> environment;
    private final Expression<TB> left;
    private final Class<TB> resultClass;

    public OngoingDeferredTensorBackedOperation(Environment<V> environment, // just line wrap
            Class<TB> resultClass, Expression<TB> left) {
        super();
        this.environment = environment;
        this.resultClass = resultClass;
        this.left = left;
    }

    public Expression<TB> plus(TB right) {
        return plus(ResolvedExpression.of(right));
    }

    public Expression<TB> plus(Expression<TB> right) {
        return elementBinaryExpression(environment.field().addition(), right);
    }

    public Expression<TB> minus(TB right) {
        return minus(ResolvedExpression.of(right));
    }

    public Expression<TB> minus(Expression<TB> right) {
        return elementBinaryExpression(environment.field().subtraction(), right);
    }

    private Expression<TB> elementBinaryExpression(BinaryOperation<V> operation, Expression<TB> right) {
        return wrapped(resultClass, elementwise(operation, extracted(left), extracted(right)));
    }

    private Expression<Tensor<V>> elementwise(BinaryOperation<V> operation, Expression<Tensor<V>> leftTensor,
            Expression<Tensor<V>> rightTensor) {
        return TensorExpressions.elementwise(operation, leftTensor, rightTensor, environment.options());
    }
}
