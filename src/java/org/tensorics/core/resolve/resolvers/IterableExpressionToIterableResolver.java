/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import static java.util.stream.Collectors.toList;

import org.tensorics.core.iterable.expressions.IterableExpressionToIterable;
import org.tensorics.core.tree.domain.ResolvingContext;

public class IterableExpressionToIterableResolver<T>
		extends AbstractResolver<Iterable<T>, IterableExpressionToIterable<T>> {

	@Override
	public boolean canResolve(IterableExpressionToIterable<T> expression, ResolvingContext context) {
		return Resolvers.contextResolvesAll(expression.getChildren(), context);
	}

	@Override
	public Iterable<T> resolve(IterableExpressionToIterable<T> expression, ResolvingContext context) {
		return expression.getChildren().stream().map(context::resolvedValueOf).collect(toList());
	}

}
