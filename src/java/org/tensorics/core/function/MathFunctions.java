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

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.function.interpolation.InterpolationStrategy;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.reduction.ToFunctions;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Preconditions;

/**
 * Provides utility method for transforming {@link MathFunction}s
 * 
 * @author caguiler, kfuchsbe
 */
public class MathFunctions {

	public static <X, Y> Tensor<DiscreteFunction<X, Y>> functionsFrom(Tensor<Y> tensor, Class<X> dimensionClass) {
		Preconditions.checkArgument(tensor.shape().dimensionality() >= 1, "tensor must contain at least one dimension");
		return Tensorics.from(tensor).reduce(dimensionClass).by(new ToFunctions<>());
	}

	public static <X, Y> DiscreteFunction<X, Y> functionFrom1DTensor(Tensor<Y> tensor, Class<X> dimensionClass) {
		Preconditions.checkArgument(tensor.shape().dimensionality() == 1, "tensor must be one-dimensional");
		return functionsFrom(tensor, dimensionClass).get();
	}

	public static <X, Y> InterpolatedFunction<X, Y> interpolated(DiscreteFunction<X, Y> function,
			InterpolationStrategy<Y> strategy, Conversion<X, Y> conversion, Comparator<X> comparator) {
		return new DefaultInterpolatedFunction<>(function, strategy, conversion, comparator);
	}

	public static <X, Y> Collection<Y> yValuesOf(DiscreteFunction<X, Y> function) {
		Preconditions.checkNotNull(function, "function cannot be null!");
		return function.definedXValues().stream().map(function::apply).collect(Collectors.toList());
	}
}
