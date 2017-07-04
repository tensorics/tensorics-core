/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import static org.tensorics.core.resolve.resolvers.Resolvers.contextResolvesAll;

import org.tensorics.core.expressions.EvaluationStatus;
import org.tensorics.core.expressions.WindowedExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * Resolves an {@link WindowedExpression} into a {@link EvaluationStatus}. The
 * enabling expression decides the resulting {@link EvaluationStatus} of the
 * expression.
 * 
 * @see WindowedExpression
 * @author acalia, caguiler
 */
public class WindowedExpressionResolver extends AbstractResolver<EvaluationStatus, WindowedExpression<Expression<?>>> {

	@Override
	public boolean canResolve(WindowedExpression<Expression<?>> expression, ResolvingContext context) {
		return contextResolvesAll(expression.getChildren(), context);
	}

	@Override
	public EvaluationStatus resolve(WindowedExpression<Expression<?>> expression, ResolvingContext context) {
		boolean isEnabled = context.resolvedValueOf(expression.enablingExpression());
		return EvaluationStatus.fromBoolean(isEnabled);
	}
}
