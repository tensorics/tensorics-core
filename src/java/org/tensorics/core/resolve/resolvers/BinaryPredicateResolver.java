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
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver that takes a predicate whose two operands are resolved and resolves it into a result.
 *
 * @param <T> the input type of the expression
 * @author caguiler
 */
public class BinaryPredicateResolver<T> extends AbstractResolver<Boolean, BinaryPredicateExpression<T>> {

    @Override
    public boolean canResolve(BinaryPredicateExpression<T> expression, ResolvingContext context) {
        return context.resolves(expression.getLeft()) && context.resolves(expression.getRight());
    }

    @Override
    public Boolean resolve(BinaryPredicateExpression<T> expression, ResolvingContext context) {
        T left = context.resolvedValueOf(expression.getLeft());
        T right = context.resolvedValueOf(expression.getRight());
        return expression.getPredicate().test(left, right);
    }
}
