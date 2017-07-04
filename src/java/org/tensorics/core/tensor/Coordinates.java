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

import static java.lang.String.format;
import static org.tensorics.core.util.MoreMultisets.containsNonUniqueElements;
import static org.tensorics.core.util.MoreMultisets.nonUniqueElementsOf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tensorics.core.util.Classes;

import com.google.common.base.Optional;
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
	@Deprecated
	public static <C> ClassToInstanceMap<C> mapOf(Iterable<? extends C> coordinates) {
		ImmutableClassToInstanceMap.Builder<C> coordinateBuilder = ImmutableClassToInstanceMap.builder();
		for (C coordinate : coordinates) {
			@SuppressWarnings("unchecked")
			Class<C> coordinateClass = (Class<C>) coordinate.getClass();
			coordinateBuilder.put(coordinateClass, coordinate);
		}
		return coordinateBuilder.build();
	}

	/**
	 * Validates dependence between given classes (interfaces) such that two
	 * interfaces in the same inheritance line are not given.
	 * 
	 * @param coordinates
	 * @throws IllegalArgumentException
	 *             when any of the given classes are linked by the inheritance
	 *             line.
	 */
	public static void checkClassesRelations(Iterable<Class<?>> coordinates) {
		for (Class<?> one : coordinates) {
			initialCheckForClassRelations(one, coordinates);
		}
	}

	/**
	 * Validates dependence between given class (interface) such that NONE of
	 * the classes can be assignable from it.
	 * 
	 * @param classToCheck
	 *            a class to verify
	 * @param coordinates
	 *            available coordinates classes
	 * @throws IllegalArgumentException
	 *             when any of the given classes are linked by the inheritance
	 *             line.
	 */
	public static void initialCheckForClassRelations(Class<?> classToCheck, Iterable<Class<?>> coordinates) {
		for (Class<?> oneToCompare : coordinates) {
			if (classToCheck.equals(oneToCompare)) {
				continue;
			}

			if (oneToCompare.isAssignableFrom(classToCheck)) {
				throw new IllegalArgumentException("Cannot use given coordinates class!'"
						+ classToCheck.getCanonicalName() + "' is assignable from '" + oneToCompare.getName() + "'");
			}
		}
	}

	/**
	 * Validates dependence between given class (interface) such that ANY o the
	 * given coordinates is assignable from it.
	 * 
	 * @param classToCheck
	 *            a class to verify
	 * @param dimensions
	 *            available coordinates classes
	 * @throws IllegalArgumentException
	 *             when any of the given classes are linked by the inheritance
	 *             line.
	 */
	public static void checkClassRelations(Class<?> classToCheck, Iterable<Class<?>> dimensions) {
		// XXX find better names
		mapToAnEntry(classToCheck, dimensions);
	}

	public static <C> Class<? super C> mapToAnEntry(Class<C> classToCheck, Iterable<Class<?>> dimensions) {
		for (Class<?> dimension : dimensions) {
			if (classToCheck.equals(dimension)) {
				@SuppressWarnings("unchecked")
				Class<? super C> toReturn = (Class<? super C>) dimension;
				return toReturn;
			}
			if (dimension.isAssignableFrom(classToCheck)) {
				@SuppressWarnings("unchecked")
				Class<? super C> toReturn = (Class<? super C>) dimension;
				return toReturn;
			}
		}
		throw new IllegalArgumentException("Cannot use given coordinates class! '" + classToCheck.getCanonicalName()
				+ "' is not assignable from any of the avaliable dimensions '" + dimensions + "'");
	}

	/**
	 * Finds the intersection of the two dimension sets, taking correctly into
	 * account the class hierarchy of the set dimensions.
	 * <p>
	 * example: If class A inherits from class B and one set contains A and the
	 * other B, then B will be returned.
	 * 
	 * @param left
	 *            one set of dimensions
	 * @param right
	 *            the other set of dimensions
	 * @return the intersection of the two sets, considering the class hierarchy
	 * @throws IllegalArgumentException
	 *             if a combination of elements of the two sets would result in
	 *             duplicated entries (e.g. the sets are not disjunct in
	 *             hierarchy)
	 */
	public static Set<Class<?>> parentClassIntersection(Set<Class<?>> left, Set<Class<?>> right) {
		Set<Class<?>> toReturn = new HashSet<>();
		for (Class<?> leftDim : left) {
			for (Class<?> rightDim : right) {
				Optional<Class<?>> higher = higherOf(leftDim, rightDim);
				if (higher.isPresent()) {
					boolean wasAdded = toReturn.add(higher.get());
					if (!wasAdded) {
						throw new IllegalArgumentException(format(
								"A combination of dimensions from %s and %s resulted twice in dimension %s."
										+ "Probably the set of dimensions are not disjunct?",
								left, right, higher.get()));
					}
				}
			}
		}
		return toReturn;
	}

	private static Optional<Class<?>> higherOf(Class<?> leftDim, Class<?> rightDim) {
		if (leftDim.isAssignableFrom(rightDim)) {
			return Optional.of(leftDim);
		}
		if (rightDim.isAssignableFrom(leftDim)) {
			return Optional.of(rightDim);
		}
		return Optional.absent();
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
		String dimensionsContext = dimensionsWithoutClassPath(tensor.context());
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

	/**
	 * Extracts from a set of coordinates, the coordinate which corresponds to
	 * the given dimension. Hereby 'corresponding' means that the cooridnate is
	 * an instance of the given dimension (class).
	 * 
	 * @param coordinates
	 *            the set of coordinates from which to extract the coordinate
	 * @param dimension
	 *            the dimension for which to find the coordinate.
	 * @return the (first) coordinate which is an instance of the given
	 *         dimension or {@code null} if none is contained in the set.
	 */
	/*
	 * TODO: Probably we should check, that there are not two mathing
	 * coordinates in the set and throw in case?
	 */
	@SuppressWarnings("unchecked")
	public static <C> C firstCoordinateOfTyp(Set<?> coordinates, Class<C> dimension) {
		for (Object positionCoordinate : coordinates) {
			if (dimension.isAssignableFrom(positionCoordinate.getClass())) {
				return (C) positionCoordinate;
			}
		}
		return null;
	}

	/**
	 * Utility method that extract the final classes of the given coordinates
	 * instances.
	 * 
	 * @param coordinates
	 *            to scan
	 * @return classes of the coordinates
	 */
	public static Set<Class<?>> getDimensionsFrom(Set<?> coordinates) {
		Set<Class<?>> classes = new HashSet<>();
		for (Object one : coordinates) {
			classes.add(one.getClass());
		}
		return classes;
	}

	/**
	 * Returns the difference of two sets of coordinates, by removing the
	 * dimensionToStrip set from the originalDimensions. This method correctly
	 * considers the class hierarchy: subclasses of the classes specified in
	 * dimensionsToStrip are also removed from originalDimensions.
	 * 
	 * @param originalDimensions
	 *            the original set of dimensions
	 * @param dimensionsToStrip
	 *            the dimensions to remove
	 * @return the original dimension set minus the dimensions to strip
	 */
	public static Set<Class<?>> parentClassDifference(Set<Class<?>> originalDimensions,
			Set<? extends Class<?>> dimensionsToStrip) {
		Set<Class<?>> toReturn = new HashSet<>(originalDimensions);
		for (Class<?> original : originalDimensions) {
			for (Class<?> toStrip : dimensionsToStrip) {
				if (toStrip.isAssignableFrom(original)) {
					toReturn.remove(original);
				}
			}
		}
		return toReturn;
	}

}
