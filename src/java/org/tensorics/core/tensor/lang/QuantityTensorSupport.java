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

import org.tensorics.core.iterable.lang.QuantityIterableSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tensor.Tensor;

/**
 * Provides starting methods for tensoric eDSL expressions, which are related to
 * tensors of quantities.
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of the scalar values (elements of the field on which the
 *            operations are based on)
 */
public class QuantityTensorSupport<S> extends QuantityIterableSupport<S> {

	private final QuantityOperationRepository<S> pseudoField;

	public QuantityTensorSupport(QuantityEnvironment<S> environment) {
		super(environment);
		this.pseudoField = new QuantityOperationRepository<>(environment);
	}

	public OngoingQuantifiedTensorOperation<S> calculate(Tensor<QuantifiedValue<S>> left) {
		return new OngoingQuantifiedTensorOperation<>(pseudoField, left);
	}

}
