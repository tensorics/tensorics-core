/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction;

import org.tensorics.core.tensor.Tensor;

/**
 * The interface for the interpolation strategy in the tensors.
 * 
 * @author agorzaws
 * @param <C>
 *            type of the coordinate, must extends {@link Comparable}
 * @param <V>
 *            type of the values in the {@link Tensor}
 */
public interface InterpolationStrategy<C, V> {

	/**
	 * Returns interpolated value for coordinate C.
	 * 
	 * @param tensorWithTheOnlyOneCoordinateOfC
	 *            the tensor where only C coordinates are kept
	 * @param coordineteToInterpolate
	 * @return the interpolated value
	 */
	V getInterpolatedValue(Tensor<V> tensorWithTheOnlyOneCoordinateOfC, C coordineteToInterpolate);

}
