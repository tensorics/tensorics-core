// @formatter:off
/*******************************************************************************
 * This file is part of tensorics.
 * <p>
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.expressions.BinaryPredicateExpression;
import org.tensorics.core.iterable.expressions.BinaryPredicateIterableExpression;
import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.tree.domain.ResolvingContext;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A resolver that takes a predicate of Iterable whose two operands are resolved and resolves it into a result.
 *
 * @param <R> the return type of the expression
 * @author caguiler
 */
public class BinaryPredicateIterableResolver<R> extends AbstractResolver<Boolean, BinaryPredicateIterableExpression<R>> {

    @Override
    public boolean canResolve(BinaryPredicateIterableExpression<R> expression, ResolvingContext context) {
        return context.resolves(expression.getLeft()) && context.resolves(expression.getRight());
    }

    @Override
    public Boolean resolve(BinaryPredicateIterableExpression<R> expression, ResolvingContext context) {
        Iterable<R> left = context.resolvedValueOf(expression.getLeft());
        R right = context.resolvedValueOf(expression.getRight());
        BinaryPredicate<R> predicate = expression.getPredicate();
        return StreamSupport.stream(left.spliterator(),true).allMatch(e -> predicate.test(e,right));
    }
}
