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

package org.tensorics.core.tensor.lang;

import org.tensorics.core.commons.lang.OngoingOperation;
import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides methods to describe operations on an already defined quantified
 * tensor. This class is part of the dsl.
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of the scalar values (elements of the fields)
 */
public class OngoingDeferredQuantifiedTensorOperation<S>
		implements OngoingOperation<Expression<Tensor<QuantifiedValue<S>>>, QuantifiedValue<S>> {

	private final QuantityOperationRepository<S> pseudoField;
	private final Expression<Tensor<QuantifiedValue<S>>> left;

	public OngoingDeferredQuantifiedTensorOperation(QuantityOperationRepository<S> pseudoField,
			Expression<Tensor<QuantifiedValue<S>>> left) {
		super();
		this.pseudoField = pseudoField;
		this.left = left;
	}

	public Expression<Tensor<QuantifiedValue<S>>> plus(Tensor<QuantifiedValue<S>> right) {
		return plus(ResolvedExpression.of(right));
	}

	@Override
	public Expression<Tensor<QuantifiedValue<S>>> plus(Expression<Tensor<QuantifiedValue<S>>> right) {
		return evaluate(right, pseudoField.addition());
	}

	public Expression<Tensor<QuantifiedValue<S>>> minus(Tensor<QuantifiedValue<S>> right) {
		return minus(ResolvedExpression.of(right));
	}

	@Override
	public Expression<Tensor<QuantifiedValue<S>>> minus(Expression<Tensor<QuantifiedValue<S>>> right) {
		return evaluate(right, pseudoField.subtraction());
	}

	@Override
	public Expression<Tensor<QuantifiedValue<S>>> elementTimes(Expression<Tensor<QuantifiedValue<S>>> right) {
		return evaluate(right, pseudoField.multiplication());
	}

	@Override
	public Expression<Tensor<QuantifiedValue<S>>> elementDividedByV(QuantifiedValue<S> value) {
		return elementDividedByQT(Tensorics.scalarOf(value));
	}

	@Override
	public Expression<Tensor<QuantifiedValue<S>>> elementTimesV(QuantifiedValue<S> right) {
		return elementTimes(ResolvedExpression.of(Tensorics.scalarOf(right)));
	}

	@Override
	public Expression<Tensor<QuantifiedValue<S>>> elementDividedBy(Expression<Tensor<QuantifiedValue<S>>> right) {
		return evaluate(right, pseudoField.division());
	}

	public Expression<Tensor<QuantifiedValue<S>>> elementDividedByQT(Tensor<QuantifiedValue<S>> right) {
		return elementDividedBy(ResolvedExpression.of(right));
	}

	private Expression<Tensor<QuantifiedValue<S>>> evaluate(Expression<Tensor<QuantifiedValue<S>>> right,
			BinaryOperation<QuantifiedValue<S>> operation) {
		ElementBinaryOperation<QuantifiedValue<S>> elementQuantifiedBinaryOperation = new ElementBinaryOperation<>(
				operation, pseudoField.environment().options());

		return new BinaryOperationExpression<>(elementQuantifiedBinaryOperation, left, right);
	}

}
