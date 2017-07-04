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

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.operations.ElementTensorBackedUnaryOperation;

/**
 * Part of the tensorics fluent API that provides starting point methods for
 * eDSL clauses that deal with calculations and manipulations of tensor backed
 * objects.
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of the scalar values (elements of the field on which all
 *            the operations are based on)
 */
public class TensorbackedSupport<S> {
	private final Environment<S> environment;

	public TensorbackedSupport(Environment<S> environment) {
		this.environment = environment;
	}

	/**
	 * Constructs a tensor backed objet of the same type as the input parameter,
	 * containing the negative values of the original one.
	 * 
	 * @param tensorBacked
	 *            to use
	 * @return a tensor backed object of the same type, containing the negative
	 *         values of the input object
	 */
	public <TB extends Tensorbacked<S>> TB negativeOf(TB tensorBacked) {
		return new ElementTensorBackedUnaryOperation<S, TB>(environment.field().additiveInversion())
				.perform(tensorBacked);
	}

	/**
	 * Allows to perform calculation on given tensor backed.
	 * 
	 * @param left
	 *            to calculate with.
	 * @return expression to calculate.
	 */
	public final <TB extends Tensorbacked<S>> OngoingTensorBackedOperation<TB, S> calculate(TB left) {
		return new OngoingTensorBackedOperation<>(environment, left);
	}
}
