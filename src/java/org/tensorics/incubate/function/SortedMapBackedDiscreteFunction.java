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

package org.tensorics.incubate.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.google.common.collect.ImmutableSortedMap;

/**
 * This implementation of {@link KeyValueFunction} only provides values at
 * discrete points of X argument. If value is requested for unavailable value an
 * {@link IllegalDiscreteFunctionUsageException} is thrown.
 * 
 * @author agorzaws
 * @param <X>
 *            function arguments type
 * @param <Y>
 *            function values type
 */
public class SortedMapBackedDiscreteFunction<X extends Comparable<X>, Y>
		implements DiscreteFunction<X, Y>, Serializable {
	private static final long serialVersionUID = 1L;

	private final SortedMap<X, Y> function;
	private final SortedMap<X, Y> errors;
	private final InterpolationStrategy<X, Y> interpolationStrategy;
	private final String name;

	// private Tensor<X, Y> functionTensor;
	/**
	 * @param builder
	 */
	SortedMapBackedDiscreteFunction(Builder<X, Y> builder) {
		super();
		this.function = builder.mapBuilder.build();
		this.errors = builder.errorsBuilder.build();
		this.interpolationStrategy = builder.interpolationStrategy;
		this.name = builder.name;
	}

	@Override
	public Y getY(X xValue) {
		Y value = function.get(xValue);
		if (value != null) {
			return value;
		}
		if (interpolationStrategy == null) {
			throw new IllegalDiscreteFunctionUsageException(
					"No interpolation Strategy found and No values can be found for given argument: " + xValue);
		}
		return interpolationStrategy.interpolate(xValue, this);
	}

	@Override
	public List<X> getXs() {
		return new ArrayList<>(function.keySet());
	}

	@Override
	public List<Y> getYs() {
		return new ArrayList<>(function.values());
	}

	@Override
	public List<Y> getYsErr() {
		return new ArrayList<>(errors.values());
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * The builder for the function based on a sorted map. This class provides
	 * methods to add values to the function.
	 * 
	 * @author kfuchsbe
	 * @param <X>
	 *            the type of the values in x-direction (Independent variable)
	 * @param <Y>
	 *            the type of the values in y-direction (Dependent variable)
	 */
	public static final class Builder<X extends Comparable<X>, Y> implements DiscreteFunctionBuilder<X, Y> {

		private static final String NO_NAME = "NO NAME";

		private String name = NO_NAME;
		private final ImmutableSortedMap.Builder<X, Y> mapBuilder = ImmutableSortedMap.naturalOrder();
		private final ImmutableSortedMap.Builder<X, Y> errorsBuilder = ImmutableSortedMap.naturalOrder();

		private InterpolationStrategy<X, Y> interpolationStrategy = null;

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

		@Override
		public DiscreteFunctionBuilder<X, Y> put(X key, Y value, Y error) {
			mapBuilder.put(key, value);
			errorsBuilder.put(key, error);
			return this;
		}

		public DiscreteFunctionBuilder<X, Y> putAll(Map<? extends X, ? extends Y> values) {
			mapBuilder.putAll(values);
			return this;
		}

		@Override
		public DiscreteFunctionBuilder<X, Y> withInterpolationStrategy(InterpolationStrategy<X, Y> strategy) {
			this.interpolationStrategy = strategy;
			return this;
		}

		@Override
		public DiscreteFunctionBuilder<X, Y> withName(String newName) {
			this.name = newName;
			return this;
		}

		@Override
		public SortedMapBackedDiscreteFunction<X, Y> build() {
			return new SortedMapBackedDiscreteFunction<>(this);
		}
	}

	public static final <X extends Comparable<X>, Y> Builder<X, Y> builder() {
		return new Builder<>();
	}

}
