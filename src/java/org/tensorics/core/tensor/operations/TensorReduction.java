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

package org.tensorics.core.tensor.operations;

import java.util.Map;
import java.util.Map.Entry;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.reduction.ReductionStrategy;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

/**
 * The operation which describes the reduction of a tensor in one direction.
 * 
 * @author kfuchsbe
 * @param <C>
 *            the dimension (direction, type of coordinate) in which the tensor
 *            will be reduced
 * @param <E>
 *            the type of the elements of the tensor
 */
public class TensorReduction<C, E, R> implements Conversion<Tensor<E>, Tensor<R>> {

	private final Class<? extends C> direction;
	private final ReductionStrategy<? super C, E, R> reductionStrategy;

	public TensorReduction(Class<? extends C> direction, ReductionStrategy<? super C, E, R> strategy) {
		super();
		this.direction = direction;
		this.reductionStrategy = strategy;
	}

	@Override
	public Tensor<R> apply(Tensor<E> value) {
		Tensor<Map<C, E>> mapped = TensorInternals.mapOut(value).inDirectionOf(direction);

		Builder<R> builder = ImmutableTensor.builder(mapped.shape().dimensionSet());
		builder.context(reductionStrategy.context(value.context()));
		for (Entry<Position, Map<C, E>> entry : mapped.asMap().entrySet()) {
			R reducedValue = reductionStrategy.reduce(entry.getValue(), entry.getKey());
			if (reducedValue != null) {
				builder.at(entry.getKey()).put(reducedValue);
			}
		}
		return builder.build();
	}

}
