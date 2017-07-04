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

import org.tensorics.core.expressions.CreationOperationExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver, which takes expressions of creation operations (operations with
 * no input parameters and one result) and resolves them to the respective
 * results.
 * 
 * @author kfuchsbe
 * @param <R>
 *            the type of the result of the expression
 */
public class CreationOperationResolver<R> extends AbstractResolver<R, CreationOperationExpression<R>> {

	@Override
	public boolean canResolve(CreationOperationExpression<R> expression, ResolvingContext context) {
		return true;
	}

	@Override
	public R resolve(CreationOperationExpression<R> expression, ResolvingContext context) {
		return expression.getOperation().perform();
	}

}
