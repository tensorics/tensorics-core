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

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.iterable.lang.ScalarIterableSupport;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryFunction;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;
import org.tensorics.core.tensor.operations.ElementUnaryOperation;
import org.tensorics.core.tensor.operations.SingleValueTensorCreationOperation;

/**
 * Extends the usage of fields by operations defined on tensors
 * 
 * @author kfuchsbe, agorzaws
 * @param <V>
 *            the type of the elements of the tensor(ic)
 */
public class TensorSupport<V> extends ScalarIterableSupport<V> {

	private final Environment<V> environment;

	public TensorSupport(Environment<V> environment) {
		super(environment.field());
		this.environment = environment;
	}

	/**
	 * Allows to perform calculation on given tensoric.
	 * 
	 * @param tensoric
	 *            to calculate with.
	 * @return expression to calculate.
	 */
	public final <C> OngoingTensorOperation<C, V> calculate(Tensor<V> tensoric) {
		return new OngoingTensorOperation<>(environment, tensoric);
	}

	/**
	 * returns a ZERO value {@link Tensor} for given {@link Shape}.
	 * 
	 * @param shape
	 *            to use.
	 * @return a {@link Tensor} of given {@link Shape} filled with 0.0;
	 */
	public Tensor<V> zeros(Shape shape) {
		return new SingleValueTensorCreationOperation<V>(shape, environment.field().zero()).perform();
	}

	/**
	 * returns a IDENTITY value {@link Tensor} for given {@link Shape}.
	 * 
	 * @param shape
	 *            to use.
	 * @return a {@link Tensor} of given {@link Shape} filled with field
	 *         identities;
	 */
	public Tensor<V> ones(Shape shape) {
		return new SingleValueTensorCreationOperation<V>(shape, environment.field().one()).perform();
	}

	/**
	 * @param tensor
	 *            to use.s
	 * @return a {@link Tensor} with field inverse values
	 */
	public Tensor<V> elementInverseOf(Tensor<V> tensor) {
		return new ElementUnaryOperation<V>(environment.field().multiplicativeInversion()).perform(tensor);
	}

	/**
	 * @param tensor
	 *            to use
	 * @return a {@link Tensor} of negative values
	 */
	public Tensor<V> negativeOf(Tensor<V> tensor) {
		return new ElementUnaryOperation<V>(environment.field().additiveInversion()).perform(tensor);
	}

	public <S> Tensor<S> elementwise(BinaryOperation<S> operation, Tensor<S> left, Tensor<S> right) {
		return new ElementBinaryOperation<>(operation, environment.options()).perform(left, right);
	}

	public <S, R> Tensor<R> elementwise(BinaryFunction<S, R> operation, Tensor<S> left, Tensor<S> right) {
		return new ElementBinaryFunction<>(operation, environment.options()).perform(left, right);
	}
}
