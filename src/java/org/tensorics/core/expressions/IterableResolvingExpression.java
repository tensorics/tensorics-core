/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import java.util.List;

import org.tensorics.core.resolve.resolvers.IterableResolvingExpressionResolver;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;

import com.google.common.collect.ImmutableList;

/**
 * Expression that given an {@link Iterable} of {@link Expression} of T,
 * resolves to an {@link Iterable} of T. In other words, it resolves the inner
 * {@link Expression} of the {@link Iterable}.
 * 
 * @see IterableResolvingExpressionResolver
 * @author acalia, caguiler, kfuchsberger
 * @param <T>
 *            type of the elements of the iterable
 */
public class IterableResolvingExpression<T> extends AbstractDeferredExpression<Iterable<T>> {

	private final List<Expression<T>> expressions;

	public IterableResolvingExpression(Iterable<Expression<T>> iterable) {
		this.expressions = ImmutableList.copyOf(iterable);
	}

	@Override
	public List<? extends Node> getChildren() {
		return expressions;
	}

	public List<? extends Expression<T>> expressions() {
		return expressions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expressions == null) ? 0 : expressions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IterableResolvingExpression<?> other = (IterableResolvingExpression<?>) obj;
		if (expressions == null) {
			if (other.expressions != null) {
				return false;
			}
		} else if (!expressions.equals(other.expressions)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "IterableResolvingExpression [expressions=" + expressions + "]";
	}

}
