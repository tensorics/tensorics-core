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
package org.tensorics.core.iterable.lang;

import org.tensorics.core.iterable.expressions.BinaryPredicateIterableExpression;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides methods to describe the right hand part of a binary predicate for
 * {@link Iterable} @{@link Expression}s
 * <p>
 * This class is part of the tensorics fluent API.
 *
 * @param <S>
 *            the type of the scalar values (elements of the field)
 * @author caguiler
 */
public class OngoingDeferredIterableBinaryPredicate<S> {

	private final ExtendedField<S> field;
	private final Expression<Iterable<S>> left;

	public OngoingDeferredIterableBinaryPredicate(ExtendedField<S> field, Expression<Iterable<S>> left) {
		this.field = field;
		this.left = left;
	}

	public Expression<Boolean> isLessThan(S value) {
		return isLessThan(ResolvedExpression.of(value));
	}

	public Expression<Boolean> isLessThan(Expression<S> expression) {
		return new BinaryPredicateIterableExpression<>(field.less(), left, expression);
	}
}
