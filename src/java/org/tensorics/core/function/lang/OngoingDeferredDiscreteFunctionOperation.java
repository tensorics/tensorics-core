// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
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

    OngoingDeferredDiscreteFunctionOperation(Environment<Y> environment, Expression<DiscreteFunction<X, Y>> left,
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
