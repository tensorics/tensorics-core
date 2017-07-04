/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import java.util.function.Predicate;

import org.tensorics.core.expressions.PredicateExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

public class PredicateConditionResolver<T> extends AbstractResolver<Boolean, PredicateExpression<T>> {

	@Override
	public boolean canResolve(PredicateExpression<T> expression, ResolvingContext context) {
		return Resolvers.contextResolvesAll(expression.getChildren(), context);
	}

	@Override
	public Boolean resolve(PredicateExpression<T> assertion, ResolvingContext context) {
		T source = context.resolvedValueOf(assertion.source());
		Predicate<T> predicate = context.resolvedValueOf(assertion.predicate());
		return predicate.test(source);
	}

}
