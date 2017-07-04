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

package org.tensorics.incubate.function;

import static javax.measure.unit.Unit.ONE;

import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Quantity;

import org.jscience.physics.amount.Amount;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Preconditions;

/**
 * @author agorzaws
 * @param <QX>
 *            type of arguments
 * @param <QY>
 *            type of values
 */
public class ExponentialFunction<QX extends Quantity, QY extends Quantity>
		implements AnalyticalFunction<Amount<QX>, Amount<QY>> {

	private final Amount<QX> inverseExpConst;
	private final Amount<QY> amplitude;

	ExponentialFunction(Builder<QX, QY> builder) {
		this.amplitude = builder.amplitude;
		this.inverseExpConst = builder.inverseExpConst;

		Preconditions.checkArgument(amplitude != null, "Argument '" + "amplitude" + "' must not be null!");
		Preconditions.checkArgument(inverseExpConst != null,
				"Argument '" + "inverseExponentialConstant" + "' must not be null!");
	}

	@Override
	public Amount<QY> getY(Amount<QX> xValue) {
		/* cast is safe since x and inverseExpConst are the same Dimension */
		@SuppressWarnings("unchecked")
		Amount<Dimensionless> divisionResult = (Amount<Dimensionless>) xValue.divide(inverseExpConst);
		return amplitude.times(Math.exp(divisionResult.doubleValue(ONE)));
	}

	@Override
	public String toText() {
		return "[y = A * exp(x / B)]";
	}

	public Amount<QY> getAmplitude() {
		return amplitude;
	}

	public Amount<QX> getInverseExponentialConstant() {
		return inverseExpConst;
	}

	/**
	 * An builder for an exponentional functions that uses jscience amounts.
	 * 
	 * @author agorzaws
	 * @param <QX>
	 *            the type of quantity in X direction
	 * @param <QY>
	 *            the type of quantity in Y direction
	 */
	public static final class Builder<QX extends Quantity, QY extends Quantity> {

		private Amount<QX> inverseExpConst;
		private Amount<QY> amplitude;

		Builder() {
			/*
			 * package private constructor to allow instantion only from the
			 * factory method
			 */
		}

		public Builder<QX, QY> withAmplitude(Amount<QY> newAmplitude) {
			this.amplitude = newAmplitude;
			return this;
		}

		public Builder<QX, QY> withInverseExponentialConstant(Amount<QX> newInverseExpConst) {
			this.inverseExpConst = newInverseExpConst;
			return this;
		}

		public ExponentialFunction<QX, QY> build() {
			return new ExponentialFunction<>(this);
		}
	}

	public static <QX extends Quantity, QY extends Quantity> Builder<QX, QY> builder() {
		return new Builder<>();
	}

	public Tensor<Amount<QY>> tensor() {
		throw new UnsupportedOperationException("Cannot get a discrete tensor from analytical function");
	}
}
