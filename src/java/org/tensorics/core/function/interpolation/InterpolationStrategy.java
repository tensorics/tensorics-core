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

package org.tensorics.core.function.interpolation;

import java.io.Serializable;
import java.util.Comparator;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.function.DiscreteFunction;

/**
 * A strategy defines how to calculate values of Y of a {@link DiscreteFunction}
 * from a finite set of values of X.
 * 
 * @author agorzaws, caguiler
 * @param <Y>
 *            the type of the dependent variable (output) of the discrete
 *            function
 */
public interface InterpolationStrategy<Y> extends Serializable, ManipulationOption {

	/**
	 * Given a value of X, a discrete function from X to Y and a conversion from
	 * X to Y, returns the interpolated value of the function at the given point
	 * 
	 * @param xValue
	 *            value of x for which you want to know the function output
	 *            value
	 * @param function
	 *            {@link DiscreteFunction} to interpolate
	 * @param conversion
	 *            defines how values of X domain are transformed into values of
	 *            Y domain. It is needed for performing any kind of
	 *            interpolation.
	 * @return the interpolated value of y for the {@code function} at
	 *         {@code xValue}
	 */
	<X> Y interpolate(X xValue, DiscreteFunction<X, Y> function, Conversion<X, Y> conversion, Comparator<X> comparator);

	@Override
	default Class<? extends ManipulationOption> getMarkerInterface() {
		return InterpolationStrategy.class;
	}
}
