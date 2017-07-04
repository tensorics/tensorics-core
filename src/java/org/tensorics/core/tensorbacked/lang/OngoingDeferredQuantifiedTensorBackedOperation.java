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

package org.tensorics.core.tensorbacked.lang;

import static org.tensorics.core.tensorbacked.expressions.TensorbackedExpressions.extracted;
import static org.tensorics.core.tensorbacked.expressions.TensorbackedExpressions.wrapped;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.expressions.TensorExpressions;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Part of the tensorcs fluent API that provides methods to describe the right
 * hand part of binary operations that act on expressions of tensor backed
 * instances with quantified values.
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of the scalars (elements of the field on which all the
 *            operations are based)
 * @param <TB>
 *            the type of the quantified tensor backed objects
 */
public class OngoingDeferredQuantifiedTensorBackedOperation<S, TB extends Tensorbacked<QuantifiedValue<S>>> {

	private final QuantityOperationRepository<S> operationRepository;
	private final Expression<TB> left;
	private final Class<TB> resultClass;

	public OngoingDeferredQuantifiedTensorBackedOperation(QuantityOperationRepository<S> operationRepository,
			Class<TB> resultClass, Expression<TB> left) {
		super();
		this.operationRepository = operationRepository;
		this.resultClass = resultClass;
		this.left = left;
	}

	public Expression<TB> plus(TB right) {
		return plus(ResolvedExpression.of(right));
	}

	public Expression<TB> plus(Expression<TB> right) {
		return elementwise(operationRepository.addition(), right);
	}

	public Expression<TB> minus(TB right) {
		return minus(ResolvedExpression.of(right));
	}

	public Expression<TB> minus(Expression<TB> right) {
		return elementwise(operationRepository.subtraction(), right);
	}

	private Expression<TB> elementwise(BinaryOperation<QuantifiedValue<S>> operation, Expression<TB> right) {
		return wrapped(resultClass, elementwise(operation, extracted(left), extracted(right)));
	}

	private <V> Expression<Tensor<V>> elementwise(BinaryOperation<V> operation, Expression<Tensor<V>> leftTensor,
			Expression<Tensor<V>> rightTensor) {
		return TensorExpressions.elementwise(operation, leftTensor, rightTensor, options());
	}

	private OptionRegistry<ManipulationOption> options() {
		return operationRepository.environment().options();
	}

}
