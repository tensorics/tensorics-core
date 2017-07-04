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

package org.tensorics.core.lang;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.tensorics.core.lang.InnerProductTest.Coord.A;
import static org.tensorics.core.lang.InnerProductTest.Coord.B;
import static org.tensorics.core.lang.InnerProductTest.Coord.C;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.variance.Covariant;
import org.tensorics.core.tensor.variance.Covariants;
import org.tensorics.core.util.Instantiator;

import com.google.common.collect.ImmutableSet;

public class InnerProductTest extends TensoricDoubleSupport {

	private Tensor<Double> contravariant123;
	private Tensor<Double> covariant246;

	@Before
	public void setUp() {
		contravariant123 = contravariant(1.0, Coord.values());
		covariant246 = covariant(2.0, Coord.values());
	}

	@Test(expected = NullPointerException.class)
	public void leftNullThrows() {
		calculate((Tensor<Double>) null).times(contravariant123);
	}

	@Test(expected = NullPointerException.class)
	public void rightNullThrows() {
		calculate(covariant246).times(null);
	}

	@Test
	public void simpleMultiplicationGivesSizeOne() {
		assertThat(simple3x3Calculation().shape().size(), equalTo(1));
	}

	@Test
	public void simpleMultiplicationGivesZeroDimensions() {
		assertThat(simple3x3Calculation().shape().dimensionality(), equalTo(0));
	}

	@Test
	public void simpleMultiplicationResultsIn28() {
		assertThat(simple3x3Calculation().get(), equalTo(28.0));
	}

	@Test
	public void calculateWithOneMissingContravariant() {
		Tensor<Double> result = calculate(contravariant(1.0, A, C)).times(covariant246);
		assertThat(result.get(), equalTo(20.0));
	}

	@Test
	public void calculateWithOneMissingCovariant() {
		Tensor<Double> result = calculate(contravariant123).times(covariant(2.0, B, C));
		assertThat(result.get(), equalTo(26.0));
	}

	@Test
	public void simpleCalculationDoesNotCareAboutOrder() {
		Tensor<Double> result = calculate(contravariant123).times(covariant246);
		assertThat(result, equalTo(simple3x3Calculation()));
	}

	@Test
	public void twoPlanesContravariantHasSizeTwo() {
		assertThat(broadcasted3x3Calculation().shape().size(), equalTo(2));
	}

	@Test
	public void twoPlanesContravariantHasOneDimension() {
		assertThat(broadcasted3x3Calculation().shape().dimensionality(), equalTo(1));
	}

	@Test
	public void twoPlanesContravariantResultsIn28ForH() {
		assertThat(broadcasted3x3Calculation().get(Plane.H), equalTo(28.0));
	}

	@Test
	public void twoPlanesContravariantResultsIn56ForV() {
		assertThat(broadcasted3x3Calculation().get(Plane.V), equalTo(56.0));
	}

	@Test
	public void coContraTimesContraResultsIn3Elements() {
		assertThat(co2Contra2Multiplication().shape().size(), equalTo(3));
	}

	@Test
	public void coContraTimesContraResultsInContraDimension() {
		assertEquals(ImmutableSet.of(Coord.class), co2Contra2Multiplication().shape().dimensionSet());
	}

	@Test
	public void coContraTimesContraResultsCEquals64() {
		Tensor<Double> result = co2Contra2Multiplication();
		assertThat(result.get(Coord.A), equalTo(40.0));
		assertThat(result.get(Coord.B), equalTo(52.0));
		assertThat(result.get(Coord.C), equalTo(64.0));
	}

	@Test
	public void coContraTimesContraDoesNotCareAboutOrder() {
		Tensor<Double> result = calculate(contravariant123).times(coAndContravariant(2.0, 2.0, Coord.values()));
		assertThat(result.get(Coord.A), equalTo(40.0));
		assertThat(result.get(Coord.B), equalTo(52.0));
		assertThat(result.get(Coord.C), equalTo(64.0));
	}

	@Test
	public void coContraTimesCoContraHasCorrectDimensions() {
		assertEquals(ImmutableSet.of(Coord.class, CoCoord.class), simpleCoContraTimesCoContra().shape().dimensionSet());
	}

	@Test
	public void coContraTimesCoContraHasCorrectNumberOfValues() {
		assertThat(simpleCoContraTimesCoContra().shape().size(), equalTo(9));
	}

	@Test
	public void coContraTimesCoContraHasCorrectValues() {
		Tensor<Double> left = coAndContravariant(1.0, 2.0, Coord.values());
		Tensor<Double> right = coAndContravariant(3.0, 4.0, Coord.values());
		/* The last "row" in the left tensor */
		assertThat(left.get(C, CoCoord.of(A)), equalTo(7.0));
		assertThat(left.get(C, CoCoord.of(B)), equalTo(8.0));
		assertThat(left.get(C, CoCoord.of(C)), equalTo(9.0));
		/* The last "column" in the right tensor */
		assertThat(right.get(A, CoCoord.of(C)), equalTo(13.0));
		assertThat(right.get(B, CoCoord.of(C)), equalTo(17.0));
		assertThat(right.get(C, CoCoord.of(C)), equalTo(21.0));
		/* the last element of the resulting tensor */
		assertThat(calculate(left).times(right).get(C, CoCoord.of(C)), equalTo(416.0));
	}

	@Test
	public void coContraTimesCoContraCaresAboutOrderSinceItTakesTheCovariantFromTheLeftTensor() {
		Tensor<Double> left = coAndContravariant(3.0, 4.0, Coord.values());
		Tensor<Double> right = coAndContravariant(1.0, 2.0, Coord.values());
		/* The last "row" in the left tensor */
		assertThat(left.get(C, CoCoord.of(A)), equalTo(15.0));
		assertThat(left.get(C, CoCoord.of(B)), equalTo(18.0));
		assertThat(left.get(C, CoCoord.of(C)), equalTo(21.0));
		/* The last "column" in the right tensor */
		assertThat(right.get(A, CoCoord.of(C)), equalTo(5.0));
		assertThat(right.get(B, CoCoord.of(C)), equalTo(7.0));
		assertThat(right.get(C, CoCoord.of(C)), equalTo(9.0));
		/* the last element of the resulting tensor */
		assertThat(calculate(left).times(right).get(C, CoCoord.of(C)), equalTo(390.0));
	}

	private Tensor<Double> simpleCoContraTimesCoContra() {
		return calculate(coAndContravariant(1.0, 2.0, Coord.values()))
				.times(coAndContravariant(3.0, 4.0, Coord.values()));
	}

	private Tensor<Double> co2Contra2Multiplication() {
		return calculate(coAndContravariant(2.0, 2.0, Coord.values())).times(contravariant123);
	}

	private Tensor<Double> broadcasted3x3Calculation() {
		return calculate(twoPlaneContravariant(1.0, Coord.values())).times(covariant246);
	}

	private Tensor<Double> simple3x3Calculation() {
		return calculate(covariant246).times(contravariant123);
	}

	public static enum Coord {
		A, B, C;
	}

	public static enum Plane {
		H, V;
	}

	public final static class CoCoord extends Covariant<Coord> {

		public CoCoord(Coord coordinate) {
			super(coordinate);
		}

		public static CoCoord of(Coord coord) {
			return new CoCoord(coord);
		}

	}

	private Tensor<Double> twoPlaneContravariant(double factor, Coord... coordinates) {
		Builder<Double> builder1 = ImmutableTensor.builder(Coord.class, Plane.class);
		for (int i = 0; i < coordinates.length; i++) {
			for (Plane plane : Plane.values()) {
				Coord coordinate = coordinates[i];
				Object[] coordinates1 = { coordinate, plane };
				builder1.put(Position.at(coordinates1), ((coordinate.ordinal() + 1) * factor * (plane.ordinal() + 1)));
			}
		}
		return builder1.build();
	}

	private Tensor<Double> contravariant(double factor, Coord... coordinates) {
		Builder<Double> builder1 = ImmutableTensor.builder(Coord.class);
		for (int i = 0; i < coordinates.length; i++) {
			Coord coordinate = coordinates[i];
			Object[] coordinates1 = { coordinate };
			builder1.put(Position.at(coordinates1), ((coordinate.ordinal() + 1) * factor));
		}
		return builder1.build();
	}

	private Tensor<Double> covariant(double factor, Coord... coordinates) {
		Instantiator<Coord, CoCoord> intantiator = Covariants.instantiatorFor(CoCoord.class);
		Builder<Double> builder1 = ImmutableTensor.builder(CoCoord.class);
		for (int i = 0; i < coordinates.length; i++) {
			Coord coordinate = coordinates[i];
			Object[] coordinates1 = { intantiator.create(coordinate) };
			builder1.put(Position.at(coordinates1), ((coordinate.ordinal() + 1) * factor));
		}
		return builder1.build();
	}

	private Tensor<Double> coAndContravariant(double coFactor, double contraFactor, Coord... coordinates) {
		Instantiator<Coord, CoCoord> intantiator = Covariants.instantiatorFor(CoCoord.class);
		Builder<Double> builder1 = ImmutableTensor.builder(Coord.class, CoCoord.class);
		for (Coord contra : coordinates) {
			for (Coord co : coordinates) {
				Object[] coordinates1 = { contra, intantiator.create(co) };
				builder1.put(Position.at(coordinates1), (((contra.ordinal() + 1) * contraFactor) + ((co.ordinal() + 1) * coFactor)));
			}
		}
		return builder1.build();
	}

}
