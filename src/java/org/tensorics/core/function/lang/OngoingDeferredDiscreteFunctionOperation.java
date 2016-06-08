/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.operations.DiscreteFunctionBinaryOperation;
import org.tensorics.core.function.operations.DiscreteFunctionOperationRepository;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.base.Function;

public class OngoingDeferredDiscreteFunctionOperation<X extends Comparable<? super X>, Y> {

    private final Expression<DiscreteFunction<X, Y>> left;
    private final DiscreteFunctionOperationRepository<X, Y> repository;

    public OngoingDeferredDiscreteFunctionOperation(Environment<Y> environment, Expression<DiscreteFunction<X, Y>> left,
            Function<X, Y> conversion) {
        this.left = left;
        this.repository = new DiscreteFunctionOperationRepository<>(environment, conversion);
    }

    public final Expression<DiscreteFunction<X, Y>> plus(Expression<DiscreteFunction<X, Y>> right) {
        return binaryExpressionOf(repository.addition(), left, right);
    }

    public Expression<DiscreteFunction<X, Y>> minus(Expression<DiscreteFunction<X, Y>> right) {
        return binaryExpressionOf(repository.subtraction(), left, right);
    }

    public Expression<DiscreteFunction<X, Y>> times(Expression<DiscreteFunction<X, Y>> right) {
        return binaryExpressionOf(repository.multiplication(), left, right);
    }

    public Expression<DiscreteFunction<X, Y>> dividedBy(Expression<DiscreteFunction<X, Y>> right) {
        return binaryExpressionOf(repository.division(), left, right);
    }

    private static <X extends Comparable<? super X>, Y> Expression<DiscreteFunction<X, Y>> binaryExpressionOf(
            DiscreteFunctionBinaryOperation<X, Y> operation, Expression<DiscreteFunction<X, Y>> left,
            Expression<DiscreteFunction<X, Y>> right) {
        return new BinaryOperationExpression<>(operation, left, right);
    }
}
