/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.functional.expressions.FunctionalExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

public class FunctionalExpressionResolver<R> extends AbstractResolver<R, FunctionalExpression<R>> {

	@Override
	public boolean canResolve(FunctionalExpression<R> expression, ResolvingContext context) {
		return Resolvers.contextResolvesAll(expression.getChildren(), context);
	}

	@Override
	public R resolve(FunctionalExpression<R> expression, ResolvingContext context) {
		Object[] arguments = expression.getChildren().stream().map(child -> context.resolvedValueOf(child)).toArray();
		return expression.function().apply(arguments);
	}

}
