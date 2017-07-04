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

import static java.util.Collections.singleton;
import static org.tensorics.core.tensor.operations.TensorInternals.entrySetOf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorInternals;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class OngoingOrderedFlattening<S, C extends Comparable<C>> {

	private final Tensor<S> tensor;
	private final Class<C> dimensionToFlatten;

	public OngoingOrderedFlattening(Tensor<S> tensor, Class<C> dimension) {
		super();
		this.tensor = tensor;
		this.dimensionToFlatten = dimension;
	}

	public Tensor<List<S>> intoTensorOfLists() {
		return Tensorics.fromMap(remainingDimensions(), Multimaps.asMap(intoListMultimap()));
	}

	private ListMultimap<Position, S> intoListMultimap() {
		ImmutableListMultimap.Builder<Position, S> builder = ImmutableListMultimap.builder();
		Tensor<Map<C, S>> maps = TensorInternals.mapOut(tensor).inDirectionOf(dimensionToFlatten);
		for (Entry<Position, Map<C, S>> entry : entrySetOf(maps)) {
			Position newPosition = entry.getKey();
			Map<C, S> map = entry.getValue();
			List<C> keys = new ArrayList<>(map.keySet());
			Collections.sort(keys);
			for (C key : keys) {
				builder.put(newPosition, map.get(key));
			}
		}
		return builder.build();
	}

	public Tensor<List<C>> intoTensorOfKeyLists() {
		return Tensorics.fromMap(remainingDimensions(), Multimaps.asMap(intoKeyListMultimap()));
	}

	private ListMultimap<Position, C> intoKeyListMultimap() {
		ImmutableListMultimap.Builder<Position, C> builder = ImmutableListMultimap.builder();
		Tensor<Map<C, S>> maps = TensorInternals.mapOut(tensor).inDirectionOf(dimensionToFlatten);
		for (Entry<Position, Map<C, S>> entry : entrySetOf(maps)) {
			Position newPosition = entry.getKey();
			Map<C, S> map = entry.getValue();
			List<C> keys = new ArrayList<>(map.keySet());
			Collections.sort(keys);
			builder.putAll(newPosition, keys);
		}
		return builder.build();
	}

	private SetView<Class<?>> remainingDimensions() {
		return Sets.difference(tensor.shape().dimensionSet(), singleton(dimensionToFlatten));
	}
}
