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

import org.tensorics.core.iterable.lang.QuantityIterableSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementUnaryOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

/**
 * Provides starting methods for tensoric eDSL expressions, which are related to
 * tensors of quantities.
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the scalar values (elements of the field on which the
 *            operations are based on)
 */
public class QuantityTensorbackedSupport<V> extends QuantityIterableSupport<V> {

	private final QuantityOperationRepository<V> pseudoField;

	public QuantityTensorbackedSupport(QuantityEnvironment<V> environment) {
		super(environment);
		this.pseudoField = new QuantityOperationRepository<>(environment);
	}

	public <QTB extends Tensorbacked<QuantifiedValue<V>>> OngoingQuantifiedTensorBackedOperation<QTB, V> calculate(
			QTB left) {
		return new OngoingQuantifiedTensorBackedOperation<>(pseudoField, left);
	}

	public <QTB extends Tensorbacked<QuantifiedValue<V>>> QTB negativeOf(QTB tensorBacked) {
		Tensor<QuantifiedValue<V>> perform = new ElementUnaryOperation<>(pseudoField.additiveInversion())
				.perform(tensorBacked.tensor());
		/* safe cast since we ensure QTB as a type in the argument! */
		@SuppressWarnings("unchecked")
		Class<QTB> tensorBackedClass = (Class<QTB>) tensorBacked.getClass();
		return TensorbackedInternals.createBackedByTensor(tensorBackedClass, perform);
	}

}
