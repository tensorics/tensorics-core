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

import static java.util.stream.Collectors.toList;
import static org.tensorics.core.util.InstantiatorType.CONSTRUCTOR;
import static org.tensorics.core.util.Instantiators.instantiatorFor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.tensorics.core.tensor.Positions;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.annotation.Dimensions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;

/**
 * This class gives an access to the methods for {@link Tensorbacked} object
 * support.
 * 
 * @author agorzaws, kfuchsbe
 */
public final class TensorbackedInternals {

	private TensorbackedInternals() {
		/* Only static methods */
	}

	/**
	 * Retrieves the dimensions from the given class inheriting from tensor
	 * backed. This is done by inspecting the {@link Dimensions} annotation.
	 * 
	 * @param tensorBackedClass
	 *            the class for which to determine the dimensions
	 * @return the set of dimentions (classses of coordinates) which are
	 *         required to create an instance of the given class.
	 */
	public static <T extends Tensorbacked<?>> Set<Class<?>> dimensionsOf(Class<T> tensorBackedClass) {
		Dimensions dimensionAnnotation = tensorBackedClass.getAnnotation(Dimensions.class);
		if (dimensionAnnotation == null) {
			throw new IllegalArgumentException(
					"No annotation of type '" + Dimensions.class + "' is present on the class '" + tensorBackedClass
							+ "'. Therefore, the dimensions of this tensorbacked type cannot be determined.");
		}
		return ImmutableSet.copyOf(dimensionAnnotation.value());
	}

	/**
	 * Creates an instance of a class backed by a tensor.
	 * 
	 * @param tensorBackedClass
	 *            the type of the class for which to create an instance.
	 * @param tensor
	 *            the tensor to back the instance
	 * @return a new instance of the given class, backed by the given tensor
	 */
	public static <V, T extends Tensorbacked<V>> T createBackedByTensor(Class<T> tensorBackedClass, Tensor<V> tensor) {
		return instantiatorFor(tensorBackedClass).ofType(CONSTRUCTOR).withArgumentType(Tensor.class).create(tensor);
	}

	static <T extends Tensorbacked<V>, V> void verifyDimensions(Class<T> tensorBackedClass, Tensor<V> tensor) {
		Set<Class<?>> targetDimensions = dimensionsOf(tensorBackedClass);
		Set<Class<?>> tensorDimensions = tensor.shape().dimensionSet();

		if (!Positions.areDimensionsConsistent(targetDimensions, tensorDimensions)) {
			throw new IllegalArgumentException("Dimensions of target class (" + targetDimensions
					+ ") do not match dimensions of given tensor (" + tensorDimensions + "). Cannot create object.");
		}
	}

	public static <S> Iterable<Tensor<S>> tensorsOf(Iterable<? extends Tensorbacked<S>> tensorbackeds) {
		List<Tensor<S>> tensors = new ArrayList<>();
		for (Tensorbacked<S> tensorbacked : tensorbackeds) {
			tensors.add(tensorbacked.tensor());
		}
		return tensors;
	}

	public static final <TB extends Tensorbacked<?>> Iterable<Shape> shapesOf(Iterable<TB> tensorbackeds) {
		return Streams.stream(tensorbackeds).map(tb -> tb.tensor().shape()).collect(toList());
	}

	@SuppressWarnings("unchecked")
	public static final <TB extends Tensorbacked<?>> Class<TB> classOf(TB tensorBacked) {
		return (Class<TB>) tensorBacked.getClass();
	}

}
