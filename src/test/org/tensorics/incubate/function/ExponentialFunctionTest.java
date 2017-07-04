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

import static javax.measure.unit.SI.AMPERE;
import static javax.measure.unit.SI.SECOND;
import static javax.measure.unit.Unit.ONE;
import static org.junit.Assert.assertNotNull;

import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Duration;
import javax.measure.quantity.ElectricCurrent;

import org.jscience.physics.amount.Amount;
import org.junit.Test;

/**
 * @author agorzaws
 */
public class ExponentialFunctionTest {

	private ExponentialFunction<Duration, ElectricCurrent> exponentialFunction;

	@Test
	public void test() {
		Amount<ElectricCurrent> current1 = Amount.valueOf(12, AMPERE);
		Amount<ElectricCurrent> current2 = Amount.valueOf(2, AMPERE);

		@SuppressWarnings("unchecked")
		Amount<Dimensionless> result = (Amount<Dimensionless>) current1.divide(current2);
		assertNotNull(result);
		System.out.println(result.getUnit());
		System.out.println(result.doubleValue(ONE));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildDefault() throws Exception {
		ExponentialFunction.builder().build();
	}

	@Test
	public void testBuildCorrectFunction() throws Exception {
		exponentialFunction = ExponentialFunction.<Duration, ElectricCurrent>builder()
				.withAmplitude(Amount.valueOf(3.0, AMPERE)).withInverseExponentialConstant(Amount.valueOf(5.0, SECOND))
				.build();
		assertNotNull(exponentialFunction);
		System.out.println(exponentialFunction.getAmplitude());
		System.out.println(exponentialFunction.getY(Amount.valueOf(5.0, SECOND)));
	}

}
