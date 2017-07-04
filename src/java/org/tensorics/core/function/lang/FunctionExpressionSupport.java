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

package org.tensorics.core.function.lang;

import java.util.Comparator;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.operations.Conversions;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;

/**
 * Specialisation of
 * {@link FunctionExpressionSupportWithConversionAndComparator} for expressions
 * of {@link DiscreteFunction}s from Y to Y.
 * 
 * @author caguiler
 * @param <Y>
 *            the type of the independent variable (input) and dependent
 *            variable (output) of the discrete function and the type of the
 *            scalar values (elements of the field) on which to operate
 */
public class FunctionExpressionSupport<Y> extends FunctionExpressionSupportWithConversionAndComparator<Y, Y> {

	public FunctionExpressionSupport(Environment<Y> environment) {
		super(environment, Conversions.identity(), environment.field().comparator());
	}

	/***
	 * @param conversion
	 *            defines how to transform a value of X type to Y type
	 * @return a {@link FunctionExpressionSupportWithConversionAndComparator}
	 *         with a given {@link Conversion} set
	 */
	public final <X> FunctionExpressionSupportWithConversionAndComparator<X, Y> withConversionAndComparator(
			Conversion<X, Y> conversion, Comparator<X> comparator) {
		return new FunctionExpressionSupportWithConversionAndComparator<>(environment(), conversion, comparator);
	}
}
