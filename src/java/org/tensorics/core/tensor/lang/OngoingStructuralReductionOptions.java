/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.reduction.AbstractInterpolationStrategy;
import org.tensorics.core.reduction.InterpolatedSlicing;
import org.tensorics.core.reduction.InterpolationStrategy;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorReduction;

/**
 * @author agorzaws
 * @param <E>
 * @param <C>
 */
public class OngoingStructuralReductionOptions<E, C> {

	private final Tensor<E> tensor;
	private final C slicePosition;
	private final Class<C> dimension;

	public OngoingStructuralReductionOptions(C slicePosition, Tensor<E> tensor, Class<C> dimension) {
		this.tensor = tensor;
		this.slicePosition = slicePosition;
		this.dimension = dimension;
	}

	/**
	 * Defines the interpolation strategy. <br>
	 * See {@link AbstractInterpolationStrategy} and its extension.
	 * 
	 * @param strategy
	 *            to use
	 * @return slicing result with interpolation between the missing comparable
	 *         coordinates.
	 */
	public Tensor<E> interpolatingWith(InterpolationStrategy<C, E> strategy) {
		return new TensorReduction<>(dimension, new InterpolatedSlicing<>(slicePosition, strategy, tensor))
				.apply(tensor);
	}

}
