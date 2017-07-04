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

package org.tensorics.core.tensor;

import static org.tensorics.core.tensor.Positions.stripping;
import static org.tensorics.core.tensor.Shapes.outerProduct;

import org.tensorics.core.tensor.Positions.DimensionStripper;

/**
 * Lets a tensors appear as a tensor with a bigger shape. The final
 * ('broadcasted') shape of the tensor has more dimensions as the original
 * tensor and is the outer product of the original shape and an additional shape
 * passed into the view. The original set and the passed in shape have to be
 * disjunct in dimensions, i.e. there must be no dimension which appears in both
 * of them.
 * <p>
 * The values are broadcasted such that the values will be the same for all
 * coordinates of the new dimensions.
 * <p>
 * The resulting view will still be backed by the original tensor. Since the
 * broadcasted shape will never be updated from the original tensor, the results
 * might be unpredictable, if the original tensor would be mutable.
 *
 * @author agorzaws
 * @author kfuchsbe
 * @param <V>
 *            the type of the values of the tensor
 */
public final class BroadcastedTensorView<V> extends AbstractTensor<V> {

	/** The original (smaller) tensor */
	private final Tensor<V> originalTensor;

	/** The shape, how the tensor will appear */
	private final Shape broadcastedShape;

	/**
	 * The stripper instance to be used for the position-transformation from the
	 * bigger shape to the original
	 */
	private final DimensionStripper dimensionStripper;

	/**
	 * Constructs a view of the given original tensor, broadcasted to the
	 * additional shape.
	 *
	 * @param originalTensor
	 *            the original tensor
	 * @param extendingShape
	 *            the shape by which the original tensor shape has to be
	 *            enlarged
	 */
	public BroadcastedTensorView(Tensor<V> originalTensor, Shape extendingShape) {
		this.originalTensor = originalTensor;
		this.broadcastedShape = outerProduct(originalTensor.shape(), extendingShape);
		this.dimensionStripper = stripping(extendingShape.dimensionSet());
	}

	@Override
	public V get(Position position) {
		return originalTensor.get(toOriginal(position));
	}

	@Override
	public V get(Object... coordinates) {
		return get(Position.of(coordinates));
	}

	private Position toOriginal(Position position) {
		return dimensionStripper.apply(position);
	}

	@Override
	public Shape shape() {
		return this.broadcastedShape;
	}

	@Override
	public Position context() {
		return originalTensor.context();
	}

}
