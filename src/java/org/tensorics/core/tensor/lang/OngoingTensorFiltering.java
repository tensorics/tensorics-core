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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map.Entry;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorInternals;

import com.google.common.collect.Range;

/**
 * Part of a fluent clause to filter elements of a tensor.
 * 
 * @author kfuchsbe
 * @param <E>
 *            the type of the tensor elements
 */
public class OngoingTensorFiltering<E> {

	private final Tensor<E> tensor;

	public OngoingTensorFiltering(Tensor<E> tensor) {
		super();
		this.tensor = tensor;
	}

	public <C extends Comparable<C>> Tensor<E> by(Class<C> coordinateClass, Range<C> coordinateRange) {
		checkNotNull(coordinateClass, "coordinateClass must not be null");
		checkNotNull(coordinateRange, "coordinateRange must not be null");

        ImmutableTensor.Builder<E> builder = ImmutableTensor.builder(tensor.shape().dimensionSet());
        builder.context(tensor.context());
        for (Entry<Position, E> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            if (coordinateRange.contains(entry.getKey().coordinateFor(coordinateClass))) {
                builder.put(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }
}
