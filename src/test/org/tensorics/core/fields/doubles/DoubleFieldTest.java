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

package org.tensorics.core.fields.doubles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.tensorics.core.math.structures.grouplike.AbelianGroup;

public class DoubleFieldTest {

	private static final double TOLERANCE = 0.00001;

	@Test
	public void additionIsNotNull() {
		assertNotNull(addition());
	}

	@Test
	public void multiplicationIsNotNull() {
		assertNotNull(multiplication());
	}

	@Test
	public void additionWorks() {
		assertEquals(2.5, addition().operation().perform(1.5, 1.0), TOLERANCE);
	}

	@Test
	public void multiplicationWorks() {
		assertEquals(5.0, multiplication().operation().perform(2.5, 2.0), TOLERANCE);
	}

	@Test
	public void zeroIsCorrect() {
		assertEquals(0.0, addition().identity(), TOLERANCE);
	}

	@Test
	public void oneIsCorrect() {
		assertEquals(1.0, multiplication().identity(), TOLERANCE);
	}

	@Test
	public void negativeWorks() {
		assertEquals(4.2, addition().inversion().perform(-4.2), TOLERANCE);
	}

	@Test
	public void inverseWorks() {
		assertEquals(0.5, multiplication().inversion().perform(2.0), TOLERANCE);
	}

	private AbelianGroup<Double> addition() {
		return field().additionStructure();
	}

	private AbelianGroup<Double> multiplication() {
		return field().multiplicationStructure();
	}

	private DoubleField field() {
		return new DoubleField();
	}

}
