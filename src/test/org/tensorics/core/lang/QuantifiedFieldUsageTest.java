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

import static javax.measure.unit.SI.AMPERE;
import static javax.measure.unit.SI.MICRO;
import static javax.measure.unit.SI.MILLI;
import static javax.measure.unit.SI.VOLT;
import static javax.measure.unit.SI.WATT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.tensorics.core.fields.doubles.Structures.doubles;

import org.junit.Test;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.lang.QuantitySupport;
import org.tensorics.core.units.JScienceUnit;

public class QuantifiedFieldUsageTest extends QuantitySupport<Double> {

	public QuantifiedFieldUsageTest() {
		super(EnvironmentImpl.of(doubles(), ManipulationOptions.defaultOptions(doubles())));
	}

	@Test
	public void testAddIndividualAsserts() {
		QuantifiedValue<Double> result = calculate(2.0, VOLT).plus(1.2, VOLT);
		assertEquals(3.2, result.value(), 0.0001);
		assertEquals(JScienceUnit.of(VOLT), result.unit());
	}

	@Test
	public void testAddOneAssert() {
		QuantifiedValue<Double> result = calculate(2.0, VOLT).plus(1.2, VOLT);
		assertEquals(valueOf(3.2, VOLT), result);
	}

	@Test
	public void testDifferentScale() {
		QuantifiedValue<Double> result = calculate(2.0, VOLT).plus(12.0, MILLI(VOLT));
		assertEquals(valueOf(2.012, VOLT), result);
	}

	@Test
	public void testDifferentScaleWithSameNonStandardUnit() {
		QuantifiedValue<Double> result = calculate(200.0, MILLI(VOLT)).plus(12.0, MILLI(VOLT));
		assertEquals(valueOf(212.0, MILLI(VOLT)), result);
	}

	@Test
	public void testDifferentScaleWithNonStandardUnit() {
		QuantifiedValue<Double> result = calculate(2000.0, MICRO(VOLT)).plus(12.0, MILLI(VOLT));
		/*
		 * If the units are not the same, then the result is always in the
		 * standard unit ...
		 */
		assertEquals(valueOf(0.014, VOLT), result);
	}

	@Test
	public void testEqualDifferentMagnitudes() throws Exception {
		/*
		 * XXX Is this ok? Should this be equal? (would need implicit unit
		 * conversion)
		 */
		assertFalse(valueOf(1.0, VOLT).equals(valueOf(1000.0, MILLI(VOLT))));
	}

	@Test
	public void testMultiply() {
		QuantifiedValue<Double> result = calculate(2.0, VOLT).times(3.0, AMPERE);
		assertEquals(valueOf(6.0, VOLT.times(AMPERE)), result);
		/*
		 * XXX The following does not work for the moment: Should it?
		 */
		// assertEquals(scalarOf(6.0, WATT), result);
	}

	@Test
	public void testMinus() {
		QuantifiedValue<Double> result = calculate(2000.0, MICRO(VOLT)).minus(12.0, MILLI(VOLT));
		assertEquals(valueOf(-0.01, VOLT), result);
	}

	@Test
	public void testDivide() {
		QuantifiedValue<Double> result = calculate(6.0, WATT).dividedBy(3.0, AMPERE);
		assertEquals(valueOf(2.0, WATT.divide(AMPERE)), result);
		/*
		 * XXX The following does not work for the moment: Should it?
		 */
		// assertEquals(scalarOf(2.0, VOLT), result);
	}
}
