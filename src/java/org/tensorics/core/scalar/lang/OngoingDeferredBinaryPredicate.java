// @formatter:off
/*******************************************************************************
 * This file is part of tensorics.
 * <p>
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.scalar.lang;

import org.tensorics.core.expressions.BinaryPredicateExpression;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides methods to describe the right hand part of a binary predicate for
 * scalar expressions.
 * <p>
 * This class is part of the tensorics fluent API.
 *
 * @param <S>
 *            the type of the scalar values (elements of the field)
 * @author caguiler
 */
public class OngoingDeferredBinaryPredicate<S> {

	private final ExtendedField<S> field;
	private final Expression<S> left;

	public OngoingDeferredBinaryPredicate(ExtendedField<S> field, Expression<S> left) {
		this.field = field;
		this.left = left;
	}

	public Expression<Boolean> isLessThan(Expression<S> expression) {
		return new BinaryPredicateExpression<>(field.less(), left, expression);
	}

	public Expression<Boolean> isLessOrEqualThan(Expression<S> expression) {
		return new BinaryPredicateExpression<>(field.lessOrEqual(), left, expression);
	}

	public Expression<Boolean> isGreaterThan(Expression<S> expression) {
		return new BinaryPredicateExpression<>(field.greater(), left, expression);
	}

	public Expression<Boolean> isGreaterOrEqualThan(Expression<S> expression) {
		return new BinaryPredicateExpression<>(field.greaterOrEqual(), left, expression);
	}

	public Expression<Boolean> isEqualTo(Expression<S> expression) {
		return new BinaryPredicateExpression<>(field.equal(), left, expression);
	}

	public Expression<Boolean> isLessThan(S value) {
		return isLessThan(ResolvedExpression.of(value));
	}

	public Expression<Boolean> isLessOrEqualThan(S value) {
		return isLessOrEqualThan(ResolvedExpression.of(value));
	}

	public Expression<Boolean> isGreaterThan(S value) {
		return isGreaterThan(ResolvedExpression.of(value));
	}

	public Expression<Boolean> isGreaterOrEqualThan(S value) {
		return isGreaterOrEqualThan(ResolvedExpression.of(value));
	}

	public Expression<Boolean> isEqualTo(S value) {
		return isEqualTo(ResolvedExpression.of(value));
	}
}
