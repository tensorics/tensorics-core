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

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.expressions.PredicateExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

public class PredicateResolver<R> extends AbstractResolver<R, PredicateExpression<R>> {

    @Override
    public boolean canResolve(PredicateExpression<R> expression, ResolvingContext context) {
        return context.resolves(expression.getLeft()) && context.resolves(expression.getRight());
    }

    @Override
    public boolean resolve(PredicateExpression<R> expression, ResolvingContext context) {
        // XXX: Predicates are resolved to Boolean Expressions independently of Expression's type
        R left = context.resolvedValueOf(expression.getLeft());
        R right = context.resolvedValueOf(expression.getRight());
        return expression.getPredicate().test(left,right);
    }
}
