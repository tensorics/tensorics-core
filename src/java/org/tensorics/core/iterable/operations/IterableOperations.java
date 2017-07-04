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

package org.tensorics.core.iterable.operations;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;

/**
 * Provides static utility methods related to iterable operations
 * 
 * @author kfuchsbe
 */
public final class IterableOperations {

	/**
	 * Private Constructor to avoid instantiation
	 */
	private IterableOperations() {
		/* Only static methods */
	}

	public static <K, V> Map<K, V> reduce(Multimap<K, V> values, IterableOperation<V> operation) {
		return reduce(values.asMap(), operation);
	}

	/**
	 * Reduces all the collection-values of the multimap by the use of the given
	 * operation and returns a map containing the result of the respective
	 * operation.
	 * 
	 * @param values
	 *            a map containg K->values, for which the values shall be
	 *            treated by the operation
	 * @param operation
	 *            the operation which shall be applied to the collection-values
	 * @return a mapt K->value, where value is the result of the operation
	 */
	private static <K, V> Map<K, V> reduce(Map<K, Collection<V>> values, IterableOperation<V> operation) {
		ImmutableMap.Builder<K, V> resultBuilder = ImmutableMap.builder();
		for (Entry<K, Collection<V>> entry : values.entrySet()) {
			resultBuilder.put(entry.getKey(), operation.apply(entry.getValue()));
		}
		return resultBuilder.build();
	}

}
