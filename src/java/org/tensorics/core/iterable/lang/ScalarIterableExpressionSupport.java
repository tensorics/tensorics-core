// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

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
        return new IterableOperationExpression<V>(repository.rms(), iterableExpression);
    }

    public Expression<V> stdOf(Iterable<V> iterable) {
        return stdOf(ResolvedExpression.of(iterable));
    }

    public Expression<V> stdOf(Expression<Iterable<V>> iterableExpression) {
        return new IterableOperationExpression<V>(repository.std(), iterableExpression);
    }

    public Expression<V> sumOfSquaresOf(Iterable<V> iterable) {
        return sumOfSquaresOf(ResolvedExpression.of(iterable));
    }

    public Expression<V> sumOfSquaresOf(Expression<Iterable<V>> iterableExpression) {
        return new IterableOperationExpression<>(repository.sumOfSquares(), iterableExpression);
    }

    public final OngoingDeferredIterableBinaryPredicate<V> testIfIt(Expression<Iterable<V>> iterableExpression) {
        return new OngoingDeferredIterableBinaryPredicate<>(field(), iterableExpression);
    }
}
