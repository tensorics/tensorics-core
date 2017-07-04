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

package org.tensorics.core.math;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.tensorics.core.commons.util.ValuePair;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.structures.grouplike.Group;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

/**
 * Contains utility methods for operations.
 * 
 * @author kfuchsbe
 */
public final class Operations {

	private Operations() {
		/* Only static methods */
	}

	public static <T> BinaryOperation<T> inverseBinaryFor(Group<T> group) {
		return new InverseOperationView<T>(group);
	}

	public static <V> List<V> performOnAll(Iterable<ValuePair<V>> valuePairs, BinaryOperation<V> operation) {
		ImmutableList.Builder<V> builder = ImmutableList.builder();
		for (ValuePair<V> valuePair : valuePairs) {
			builder.add(operation.perform(valuePair.left(), valuePair.right()));
		}
		return builder.build();
	}

	public static <K, V> ListMultimap<K, V> mapAll(Multimap<K, ValuePair<V>> valuePairs, BinaryOperation<V> operation) {
		ImmutableListMultimap.Builder<K, V> builder = ImmutableListMultimap.builder();
		for (Entry<K, Collection<ValuePair<V>>> entry : valuePairs.asMap().entrySet()) {
			builder.putAll(entry.getKey(), performOnAll(entry.getValue(), operation));
		}
		return builder.build();
	}

	private static final class InverseOperationView<T> implements BinaryOperation<T> {
		private final Group<T> group;

		InverseOperationView(Group<T> group) {
			super();
			this.group = group;
		}

		@Override
		public T perform(T left, T right) {
			return group.operation().perform(left, group.inversion().perform(right));
		}

	}

}
