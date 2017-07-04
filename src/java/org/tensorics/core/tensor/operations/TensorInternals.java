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

import static java.util.Objects.requireNonNull;
import static org.tensorics.core.tensor.operations.PositionFunctions.forSupplier;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.tensorics.core.tensor.Mappable;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableMap;

/**
 * Utility methods for tensors which are not exposed to the public API
 * 
 * @author kfuchsbe
 */
public final class TensorInternals {

	private TensorInternals() {
		/* only static methods */
	}

	public static <T> OngoingMapOut<T> mapOut(Tensor<T> tensor) {
		return new OngoingMapOut<>(tensor);
	}

	public static <T> Set<Entry<Position, T>> entrySetOf(Tensor<T> tensor) {
		return mapFrom(tensor).entrySet();
	}

	public static <S> Tensor<S> sameValues(Shape shape, S value) {
		return new SingleValueTensorCreationOperation<S>(shape, value).perform();
	}

	public static <S> Tensor<S> createFrom(Shape shape, Supplier<S> supplier) {
		return new FunctionTensorCreationOperation<>(shape, forSupplier(supplier)).perform();
	}

	public static <S> Tensor<S> createFrom(Shape shape, Function<Position, S> function) {
		return new FunctionTensorCreationOperation<>(shape, function).perform();
	}

	/**
	 * Returns a map representing the content of the given tensor. The concrete
	 * instance of the map might differ depending on the implementation of the
	 * passed in tensor: Tensor implementations can offer a more efficient way
	 * to retrieve a map from them, by implementing the {@link Mappable}
	 * interface. If this interface is present, then its
	 * {@link Mappable#asMap()} method will be called. Otherwise, as a fallback,
	 * a new immutable map will be created from information from the shape of
	 * the passed in tensor and its values.
	 * 
	 * @param tensor
	 *            the tensor from which a map should be returned
	 * @return a map representing the content of the tensor
	 * @throws NullPointerException
	 *             in case the passed in tensor is {@code null}
	 */
	public static <V> Map<Position, V> mapFrom(Tensor<V> tensor) {
		requireNonNull(tensor, "tensor must not be null");
		if (tensor instanceof Mappable) {
			@SuppressWarnings("unchecked")
			Mappable<V> mappable = (Mappable<V>) tensor;
			return mappable.asMap();
		}
		ImmutableMap.Builder<Position, V> builder = ImmutableMap.builder();
		for (Position position : tensor.shape().positionSet()) {
			builder.put(position, tensor.get(position));
		}
		return builder.build();
	}

}
