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

package org.tensorics.core.tensor;

import static org.tensorics.core.util.MoreMultisets.containsNonUniqueElements;
import static org.tensorics.core.util.MoreMultisets.nonUniqueElementsOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.tensorics.core.util.Classes;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;

/**
 * Utility methods to handle coordinates
 * 
 * @author kfuchsbe
 */
public final class Coordinates {

	private Coordinates() {
		/* only static methods */
	}

	/**
	 * Creates a class to instance map, from the given coordinates. The map will
	 * contain the classes of the coordinates as keys and the coordinates
	 * themselves as values. Duplicate keys (dimensions) are not allowed and
	 * will result in an {@link IllegalArgumentException}.
	 * 
	 * @param coordinates
	 *            the coordinates to be added to the map
	 * @return an immutable map from dimensions (coordinate classes) to
	 *         coordinate
	 * @throws IllegalArgumentException
	 *             if more than one coordinate per dimension are provided
	 * @deprecated 
	 */
	public static <C> ClassToInstanceMap<C> mapOf(Iterable<? extends C> coordinates) {
		ImmutableClassToInstanceMap.Builder<C> coordinateBuilder = ImmutableClassToInstanceMap.builder();
		for (C coordinate : coordinates) {
			@SuppressWarnings("unchecked")
			Class<C> coordinateClass = (Class<C>) coordinate.getClass();
			coordinateBuilder.put(coordinateClass, coordinate);
		}
		return coordinateBuilder.build();
	}

	public static <C> ClassToInstanceMap<C> new_mapOf(Iterable<? extends C> coordinates) {
		return null;
	}
	
	/**
	 * Provides the way to reduce long classpath names of the coordinates
	 * classes to only short Class names. It produces a combination of the
	 * tensor and it's context result.
	 * 
	 * @param tensor
	 *            to extract the dimension set and reduce their length of the
	 *            output string
	 * @return a reduced string
	 */
	public static String dimensionsWithoutClassPath(Tensor<?> tensor) {
		String dimensions = dimensionsWithoutClassPath(tensor.shape().dimensionSet());
		String dimensionsContext = dimensionsWithoutClassPath(tensor.context().getPosition());
		return "Tensor:" + dimensions + ", Context:" + dimensionsContext;
	}

	/**
	 * Provides the way to reduce long classpath names of the coordinates
	 * classes to only short Class names.
	 * 
	 * @param position
	 *            to extract the dimension set and reduce their ength of the
	 *            output string
	 * @return a reduced string
	 */
	public static String dimensionsWithoutClassPath(Position position) {
		return dimensionsWithoutClassPath(position.dimensionSet());
	}

	/**
	 * Provides the way to reduce long classpath names of the coordinates
	 * classes to only short Class names.
	 * 
	 * @param dimensionSet
	 *            to reduce length of the output string
	 * @return a reduced string
	 */
	public static String dimensionsWithoutClassPath(Set<Class<?>> dimensionSet) {
		List<String> classNames = new ArrayList<String>();
		for (Class<?> oneClass : dimensionSet) {
			classNames.add(oneClass.getSimpleName());
		}
		return classNames.toString();
	}

	public static Set<?> requireValidCoordinates(Iterable<?> coordinates) {
		requireValidDimensions(Classes.classesOf(coordinates));
		return ImmutableSet.copyOf(coordinates);
	}

	public static Set<Class<?>> requireValidDimensions(Multiset<Class<?>> dimensions) {
		if (containsNonUniqueElements(dimensions)) {
			throw new IllegalArgumentException(
					"Only unique dimensions are allowed. The following dimensions are not unique: "
							+ nonUniqueElementsOf(dimensions));
		}
		return ImmutableSet.copyOf(dimensions.elementSet());
	}

}
