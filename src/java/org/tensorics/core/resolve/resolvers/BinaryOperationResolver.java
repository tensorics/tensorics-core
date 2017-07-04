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
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver that takes binary operations and resolves them to their result. It
 * can only do so, if the two operands are resolved.
 * 
 * @author kfuchsbe
 * @param <R>
 *            the return type of the expression
 */
public class BinaryOperationResolver<R> extends AbstractResolver<R, BinaryOperationExpression<R>> {

	@Override
	public boolean canResolve(BinaryOperationExpression<R> expression, ResolvingContext context) {
		return context.resolves(expression.getLeft()) && context.resolves(expression.getRight());
	}

	@Override
	public R resolve(BinaryOperationExpression<R> expression, ResolvingContext context) {
		R left = context.resolvedValueOf(expression.getLeft());
		R right = context.resolvedValueOf(expression.getRight());
		return expression.getOperation().perform(left, right);
	}

}
