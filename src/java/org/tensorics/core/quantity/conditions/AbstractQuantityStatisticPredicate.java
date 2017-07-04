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

package org.tensorics.core.quantity.conditions;

import org.tensorics.core.fields.doubles.DoubleStatistics;
import org.tensorics.core.math.Cheating;
import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantitySubtraction;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * Base class for quantity conditions based on statistical tests. This class
 * provides utility methods to implementations for doing calculations and
 * statistics based on the values (mean) and the errors of quantities.
 * 
 * @author mihostet
 * @param <S>
 *            the value type
 */
public abstract class AbstractQuantityStatisticPredicate<S> extends ScalarSupport<S>
		implements BinaryPredicate<QuantifiedValue<S>> {

	protected final QuantityEnvironment<S> mathsEnvironment;

	protected AbstractQuantityStatisticPredicate(QuantityEnvironment<S> environment) {
		super(environment.field());
		this.mathsEnvironment = environment;
	}

	/**
	 * Evaluate the inverse Gaussian Cumulative Distribution Function and return
	 * the result as an element of the backing field. This method uses
	 * {@link Cheating} for the time being.
	 * 
	 * @param value
	 * @return
	 */
	protected S inverseGaussianCumulativeDistributionFunction(S value) {
		@SuppressWarnings("deprecation")
		final Cheating<S> cheating = mathsEnvironment.field().cheating();
		return cheating
				.fromDouble(DoubleStatistics.inverseGaussianCumulativeDistributionFunction(cheating.toDouble(value)));
	}

	/**
	 * Calculates the difference of two quantities (including error propagation)
	 * 
	 * @param left
	 *            left operand
	 * @param right
	 *            right operand
	 * @return the difference as a quantity
	 */
	protected QuantifiedValue<S> subtractQuantities(QuantifiedValue<S> left, QuantifiedValue<S> right) {
		return (new QuantitySubtraction<S>(mathsEnvironment)).perform(left, right);
	}

	/**
	 * Calculate the z-test z value for the difference of the two values (number
	 * of sigmas the difference of the mean values is away from zero)
	 * 
	 * @param left
	 *            the left operand
	 * @param right
	 *            the right operand
	 * @return the z value
	 */
	protected S zTestValueForDifference(QuantifiedValue<S> left, QuantifiedValue<S> right) {
		final QuantifiedValue<S> leftWithError = ImmutableQuantifiedValue.of(left.value(), left.unit())
				.withError(left.error().or(zero()));
		final QuantifiedValue<S> rightWithError = ImmutableQuantifiedValue.of(right.value(), right.unit())
				.withError(right.error().or(zero()));
		final QuantifiedValue<S> difference = subtractQuantities(leftWithError, rightWithError);

		return calculate(difference.value()).dividedBy(difference.error().get());
	}

}
