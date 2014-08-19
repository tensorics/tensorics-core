/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.lang;

import org.tensorics.core.iterable.expressions.IterableOperationExpression;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarExpressionSupport;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides utility methods for acting on expressions iterables of field elements.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the field
 */
public class ScalarIterableExpressionSupport<V> extends ScalarExpressionSupport<V> {

    private final IterableOperationRepository<V> repository;
    
    public ScalarIterableExpressionSupport(ExtendedField<V> field) {
        super(field);
        this.repository = new IterableOperationRepository<>(field);
    }

    public final Expression<V> averageOf(Iterable<V> iterable) {
        return averageOf(ResolvedExpression.of(iterable));
    }

    public final Expression<V> averageOf(Expression<Iterable<V>> iterableExpression) {
        return calculate(sumOf(iterableExpression)).dividedBy(sizeOf(iterableExpression));
    }

    public final Expression<V> sizeOf(Iterable<V> iterable) {
        return sizeOf(ResolvedExpression.of(iterable));
    }

    public final Expression<V> sizeOf(Expression<Iterable<V>> iterableExpression) {
        return new IterableOperationExpression<V>(repository.size(), iterableExpression);
    }

    public final Expression<V> sumOf(Iterable<V> iterable) {
        return sumOf(ResolvedExpression.of(iterable));
    }

    public final Expression<V> sumOf(Expression<Iterable<V>> iterableExpression) {
        return new IterableOperationExpression<>(repository.sum(), iterableExpression);
    }

    public Expression<V> rmsOf(Iterable<V> iterable) {
        return rmsOf(ResolvedExpression.of(iterable));
    }

    public Expression<V> rmsOf(Expression<Iterable<V>> iterableExpression) {
        return squareRootOf(calculate(sumOfSquaresOf(iterableExpression)).dividedBy(sizeOf(iterableExpression)));
    }

    public Expression<V> sumOfSquaresOf(Iterable<V> iterable) {
        return sumOfSquaresOf(ResolvedExpression.of(iterable));
    }

    public Expression<V> sumOfSquaresOf(Expression<Iterable<V>> iterableExpression) {
        return new IterableOperationExpression<>(repository.sumOfSquares(), iterableExpression);
    }

}
