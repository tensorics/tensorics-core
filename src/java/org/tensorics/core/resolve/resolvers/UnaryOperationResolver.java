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

import org.tensorics.core.expressions.UnaryOperationExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver for all kind of unary operations. It simply calls the operations functional method with the resolved
 * argument.
 * 
 * @author kfuchsbe
 * @param <R> the return type of the unary operation
 */
public class UnaryOperationResolver<R> extends AbstractResolver<R, UnaryOperationExpression<R>> {

    @Override
    public boolean canResolve(UnaryOperationExpression<R> expression, ResolvingContext context) {
        return context.resolves(expression.getOperand());
    }

    @Override
    public R resolve(UnaryOperationExpression<R> expression, ResolvingContext context) {
        R operand = context.resolvedValueOf(expression.getOperand());
        return expression.getOperation().perform(operand);
    }

}
