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
package org.tensorics.core.function.operations;

import java.util.Comparator;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.options.Environment;

/**
 * <p>
 * Contains instances of operations on discrete functions, based on an
 * environment and with a conversion.
 * <p>
 * The main purpose is to be able to re-use the instances of the operations, in
 * order to avoid to have to re-create them all the time.
 * 
 * @author caguiler
 * @param <X>
 *            the type of the independent variable (input) of the discrete
 *            function
 * @param <Y>
 *            the type of the dependent variable (output)of the discrete
 *            function and type of the elements of the environment on which the
 *            operations are based.
 */
public class DiscreteFunctionOperationRepository<X, Y> {

	private final DiscreteFunctionAddition<X, Y> addition;
	private final DiscreteFunctionSubtraction<X, Y> subtraction;
	private final DiscreteFunctionMultiplication<X, Y> multiplication;
	private final DiscreteFunctionDivision<X, Y> division;

	public DiscreteFunctionOperationRepository(Environment<Y> environment, Conversion<X, Y> conversion,
			Comparator<X> comparator) {
		addition = new DiscreteFunctionAddition<>(environment, conversion, comparator);
		subtraction = new DiscreteFunctionSubtraction<>(environment, conversion, comparator);
		multiplication = new DiscreteFunctionMultiplication<>(environment, conversion, comparator);
		division = new DiscreteFunctionDivision<>(environment, conversion, comparator);
	}

	public DiscreteFunctionAddition<X, Y> addition() {
		return addition;
	}

	public DiscreteFunctionSubtraction<X, Y> subtraction() {
		return subtraction;
	}

	public DiscreteFunctionMultiplication<X, Y> multiplication() {
		return multiplication;
	}

	public DiscreteFunctionDivision<X, Y> division() {
		return division;
	}
}
