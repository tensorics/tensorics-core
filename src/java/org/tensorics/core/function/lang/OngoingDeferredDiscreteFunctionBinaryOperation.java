// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
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

package org.tensorics.core.function.lang;

import java.util.Comparator;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.operations.AbstractDiscreteFunctionBinaryOperation;
import org.tensorics.core.function.operations.DiscreteFunctionOperationRepository;
import org.tensorics.core.tree.domain.Expression;

/**
 * Provides methods to describe the right hand part of a binary operation for
 * {@link DiscreteFunction} @ {@link Expression}s
 * 
 * @param <X>
 *            the type of the independent variable (input) of the discrete
 *            function
 * @param <Y>
 *            the type of the dependent variable (output) of the discrete
 *            function and the type of the scalar values (elements of the field)
 *            on which to operate
 * @author caguiler
 */
public class OngoingDeferredDiscreteFunctionBinaryOperation<X, Y> {

	private final Expression<DiscreteFunction<X, Y>> left;
	private final DiscreteFunctionOperationRepository<X, Y> repository;

	OngoingDeferredDiscreteFunctionBinaryOperation(Environment<Y> environment, Expression<DiscreteFunction<X, Y>> left,
			Conversion<X, Y> conversion, Comparator<X> comparator) {
		this.left = left;
		this.repository = new DiscreteFunctionOperationRepository<>(environment, conversion, comparator);
	}

	public final Expression<DiscreteFunction<X, Y>> plus(Expression<DiscreteFunction<X, Y>> right) {
		return binaryExpressionOf(repository.addition(), left, right);
	}

	public final Expression<DiscreteFunction<X, Y>> minus(Expression<DiscreteFunction<X, Y>> right) {
		return binaryExpressionOf(repository.subtraction(), left, right);
	}

	public final Expression<DiscreteFunction<X, Y>> times(Expression<DiscreteFunction<X, Y>> right) {
		return binaryExpressionOf(repository.multiplication(), left, right);
	}

	public final Expression<DiscreteFunction<X, Y>> dividedBy(Expression<DiscreteFunction<X, Y>> right) {
		return binaryExpressionOf(repository.division(), left, right);
	}

	private static <X, Y> Expression<DiscreteFunction<X, Y>> binaryExpressionOf(
			AbstractDiscreteFunctionBinaryOperation<X, Y> operation, Expression<DiscreteFunction<X, Y>> left,
			Expression<DiscreteFunction<X, Y>> right) {
		return new BinaryOperationExpression<>(operation, left, right);
	}
}
