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

import org.tensorics.core.lang.TensoricScript;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * Resolves a tensoric script to its value. A tensoric script is nothing else
 * then an expression itself, described as a tree of expressions.
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the field elements on which the tensoric expressions
 *            are based
 * @param <R>
 *            the return type of the expression
 */
public class TensoricScriptResolver<V, R> extends AbstractResolver<R, TensoricScript<V, R>> {

	@Override
	public boolean canResolve(TensoricScript<V, R> expression, ResolvingContext context) {
		return context.resolves(expression.getInternalExpression());
	}

	@Override
	public R resolve(TensoricScript<V, R> expression, ResolvingContext context) {
		return context.resolvedValueOf(expression.getInternalExpression());
	}

}
