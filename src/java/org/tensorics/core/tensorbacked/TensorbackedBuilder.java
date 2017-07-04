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

package org.tensorics.core.tensorbacked;

import static org.tensorics.core.tensorbacked.TensorbackedInternals.createBackedByTensor;
import static org.tensorics.core.tensorbacked.TensorbackedInternals.dimensionsOf;

import java.util.Map;
import java.util.Set;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

/**
 * A builder for tensor backed objects, which takes care that only positions
 * which are compatible with the dimensions of the foreseen underlaying tensor
 * are put into it. Internally, it uses a builder for a tensor, to which most of
 * the methods are delegated. At build time, the tensor is encapsulated in the
 * according tensor backed class.
 * <p>
 * This class is not thread safe.
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the values of the tensor (and thus also the tensor
 *            backed object)
 * @param <TB>
 *            the type of the tensor backed object
 */
public class TensorbackedBuilder<V, TB extends Tensorbacked<V>> {

	private final Class<TB> tensorbackedClass;
	private final ImmutableTensor.Builder<V> tensorBuilder;

	/**
	 * Constructor for the builder, which takes the target class of the
	 * tensorbacked as an argument. Will only be instantiated by the
	 * neighbouring utility class. Therefore it is package private.
	 */
	TensorbackedBuilder(Class<TB> tensorbackedClass) {
		this.tensorbackedClass = tensorbackedClass;
		this.tensorBuilder = ImmutableTensor.builder(dimensionsOf(tensorbackedClass));
	}

	public final TensorbackedBuilder<V, TB> put(java.util.Map.Entry<Position, V> entry) {
		tensorBuilder.put(entry);
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAt(V value, Position position) {
		tensorBuilder.put(position, value);
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAt(V value, Object... coordinates) {
		tensorBuilder.put(Position.at(coordinates), value);
		return this;
	}
	
	public final TensorbackedBuilder<V, TB> putAt(V value, Set<?> coordinates) {
		tensorBuilder.put(Position.at(coordinates), value);
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAll(Set<java.util.Map.Entry<Position, V>> entries) {
		for (java.util.Map.Entry<Position, V> entry : entries) {
			tensorBuilder.put(entry);
		}
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAllAt(Tensor<V> tensor, Position position) {
		tensorBuilder.putAll(position, tensor);
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAllAt(Tensorbacked<V> tensorbacked, Position position) {
		tensorBuilder.putAll(position, tensorbacked.tensor());
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAllAt(Tensor<V> tensor, Object... coordinates) {
		tensorBuilder.putAll(Position.at(coordinates), tensor);
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAllAt(Tensorbacked<V> tensorbacked, Object... coordinates) {
		tensorBuilder.putAll(Position.at(coordinates), tensorbacked.tensor());
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAll(TB tensorBacked) {
		tensorBuilder.putAll(tensorBacked.tensor());
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAll(Tensor<V> tensor) {
		tensorBuilder.putAll(tensor);
		return this;
	}

	public final TensorbackedBuilder<V, TB> putAllMap(Map<Position, V> newEntries) {
		tensorBuilder.putAll(newEntries);
		return this;
	}

	public final TensorbackedBuilder<V, TB> removeAt(Position position) {
		tensorBuilder.remove(position);
		return this;
	}

	public final TensorbackedBuilder<V, TB> context(Position context) {
		tensorBuilder.context(context);
		return this;
	}

	/**
	 * @deprecated use {@link #context(Position)}
	 */
	@Deprecated
	public final TensorbackedBuilder<V, TB> withContext(Position context) {
		return this.context(context);
	}

	public final TensorbackedBuilder<V, TB> context(Object... coordinates) {
		return this.context(Position.of(coordinates));
	}

	/**
	 * @deprecated use {@link #context(Object...)}
	 */
	@Deprecated
	public final TensorbackedBuilder<V, TB> withContext(Object... coordinates) {
		return this.context(coordinates);
	}

	/**
	 * Builds the tensor backed object, after all the content is set.
	 * 
	 * @return a new instance of the tensor backed object, containing all the
	 *         data as described after instantiating the builder.
	 */
	public TB build() {
		return createBackedByTensor(tensorbackedClass, tensorBuilder.build());
	}

}
