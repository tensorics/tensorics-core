/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.operations;

import java.util.Map;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.reduction.ReductionStrategy;
import org.tensorics.core.tensor.Context;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
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
public class TensorReduction<C, E> implements UnaryOperation<Tensor<E>> {

	private final Class<? extends C> direction;
	private final ReductionStrategy<? super C, E> reductionStrategy;

	public TensorReduction(Class<? extends C> direction,
			ReductionStrategy<? super C, E> strategy) {
		super();
		this.direction = direction;
		this.reductionStrategy = strategy;
	}

	@Override
	public Tensor<E> perform(Tensor<E> value) {
		Tensor<Map<C, E>> mapped = TensorInternals.mapOut(value).inDirectionOf(
				direction);

		Builder<E> builder = Tensorics.builder(mapped.shape().dimensionSet());
		builder.setTensorContext(Context.of(reductionStrategy.context()
				.coordinates()));
		for (Tensor.Entry<Map<C, E>> entry : mapped.entrySet()) {
			E reducedValue = reductionStrategy.reduce(entry.getValue());
			if (reducedValue != null) {
				builder.at(entry.getPosition()).put(reducedValue);
			}
		}
		return builder.build();
	}

}
