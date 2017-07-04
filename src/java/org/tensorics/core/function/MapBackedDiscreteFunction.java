// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
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

package org.tensorics.core.function;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

/**
 * Implementation of {@link DiscreteFunction} backed by a {@link Map}
 * 
 * @author caguiler
 * @see DiscreteFunction
 * @param <X>
 *            the type of the values in x-direction (Independent variable)
 * @param <Y>
 *            the type of the values in y-direction (Dependent variable)
 */
public class MapBackedDiscreteFunction<X, Y> implements DiscreteFunction<X, Y>, Serializable {
	private static final long serialVersionUID = 1L;
	private final ImmutableMap<X, Y> function;

	MapBackedDiscreteFunction(Builder<X, Y> builder) {
		this.function = builder.mapBuilder.build();
	}

	@Override
	public Y apply(X input) {
		if (!function.containsKey(input)) {
			throw new IllegalDiscreteFunctionUsageException("No value can be found for given argument: " + input);
		}

		return function.get(input);
	}

	public static final <X, Y> DiscreteFunction<X, Y> fromMap(Map<? extends X, ? extends Y> function) {
		return MapBackedDiscreteFunction.<X, Y>builder().putAll(function).build();
	}

	@Override
	public Set<X> definedXValues() {
		return function.keySet();
	}

	@Override
	public int hashCode() {
		return 31 + ((function == null) ? 0 : function.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MapBackedDiscreteFunction)) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		MapBackedDiscreteFunction other = (MapBackedDiscreteFunction) obj;
		if (function == null) {
			if (other.function != null) {
				return false;
			}
		} else if (!function.equals(other.function)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MapBackedDiscreteFunction [function=" + function + "]";
	}

	/**
	 * The builder for the function based on a map. This class provides methods
	 * to add values to the function.
	 * 
	 * @author kfuchsbe, caguiler
	 * @param <X>
	 *            the type of the independent variable (input)
	 * @param <Y>
	 *            the type of the dependent variable (output)
	 */
	public static final class Builder<X, Y> implements DiscreteFunctionBuilder<X, Y> {

		private final ImmutableMap.Builder<X, Y> mapBuilder = ImmutableMap.builder();

		Builder() {
			/* Should only be instantiated from the static method */
		}

		public DiscreteFunctionBuilder<X, Y> put(Entry<? extends X, ? extends Y> entry) {
			mapBuilder.put(entry);
			return this;
		}

		@Override
		public DiscreteFunctionBuilder<X, Y> put(X key, Y value) {
			mapBuilder.put(key, value);
			return this;
		}

		public DiscreteFunctionBuilder<X, Y> putAll(Map<? extends X, ? extends Y> values) {
			mapBuilder.putAll(values);
			return this;
		}

		@Override
		public MapBackedDiscreteFunction<X, Y> build() {
			return new MapBackedDiscreteFunction<>(this);
		}
	}

	public static final <X, Y> Builder<X, Y> builder() {
		return new Builder<>();
	}

}
