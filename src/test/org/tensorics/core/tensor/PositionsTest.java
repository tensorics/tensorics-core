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
package org.tensorics.core.tensor;

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.tensor.Positions.union;
import static org.tensorics.core.testing.TestUtil.assertUtilityClass;

import org.junit.Test;

public class PositionsTest {

	private final static Position POS_A = Position.of("A");
	private final static Position POS_B = Position.of("B");
	private final static Position POS_1 = Position.of(1);
	private final static Position POS_A1 = Position.of("A", 1);
	private final static Position POS_1A = Position.of(1, "A");

	@Test
	public void verifyUtilityClass() {
		assertUtilityClass(Positions.class);
	}

	@Test
	public void pos1AEqualsPosA1() {
		assertEquals(POS_1A, POS_A1);
	}

	@Test(expected = NullPointerException.class)
	public void leftNullThrowsException() {
		union(null, Position.empty());
	}

	@Test(expected = NullPointerException.class)
	public void rightNullThrowsException() {
		union(Position.empty(), null);
	}

	@Test
	public void unionEmptyWithEmpyIsEmpty() {
		Position result = union(Position.empty(), Position.empty());
		assertEquals(Position.empty(), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void overlappingDimensionsThrowException() {
		union(POS_A, POS_B);
	}

	@Test
	public void unionWithNonOverlappingDimensionsWorksCorrectly() {
		assertEquals(POS_1A, union(POS_1, POS_A));
	}

}
