/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static org.tensorics.core.tensor.operations.TensorInternals.mapFrom;

import java.util.Map;

/**
 * Provides sceletal behaviour of tensors. It is highly recommended that all
 * implementations of this tensor inherit from this class. This is particularly
 * important in order to respect the equality of tensors.
 *
 * @author kfuchsbe
 * @param <V>
 *            the type of the values of the tensor
 */
public abstract class AbstractTensor<V> implements Tensor<V> {

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		Position context = context();
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		Shape shape = shape();
		result = prime * result + ((shape == null) ? 0 : shape.hashCode());
		Map<Position, V> map = mapFrom(this);
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Tensor)) {
			return false;
		}
		Tensor<?> other = (Tensor<?>) obj;
		if (context() == null) {
			if (other.context() != null) {
				return false;
			}
		} else if (!context().equals(other.context())) {
			return false;
		}
		if (shape() == null) {
			if (other.shape() != null) {
				return false;
			}
		} else if (!shape().equals(other.shape())) {
			return false;
		}
		Map<Position, V> map = mapFrom(this);
		if (map == null) {
			if (mapFrom(other) != null) {
				return false;
			}
		} else if (!map.equals(mapFrom(other))) {
			return false;
		}

		return true;
	}

}
