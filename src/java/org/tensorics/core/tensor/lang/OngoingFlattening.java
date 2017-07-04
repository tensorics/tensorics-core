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

package org.tensorics.core.tensor.lang;

import java.util.Set;

import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableSet;

public final class OngoingFlattening<S> {

	private final Tensor<S> tensor;

	OngoingFlattening(Tensor<S> tensor) {
		super();
		this.tensor = tensor;
	}

	public OngoingDimensionFlattening<S> inDirectionOf(Class<?> dimension) {
		return inDirectionsOf(ImmutableSet.<Class<?>>of(dimension));
	}

	public OngoingDimensionFlattening<S> inDirectionsOf(Class<?>... dimensions) {
		return inDirectionsOf(ImmutableSet.<Class<?>>copyOf(dimensions));
	}

	public OngoingDimensionFlattening<S> inDirectionsOf(Set<Class<?>> dimensions) {
		return new OngoingDimensionFlattening<S>(tensor, dimensions);
	}

	public <C extends Comparable<C>> OngoingOrderedFlattening<S, C> orderedInDirectionOf(Class<C> dimension) {
		return new OngoingOrderedFlattening<S, C>(tensor, dimension);
	}
}
