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

import static org.tensorics.core.tensor.Positions.assertConsistentDimensions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableSet;

public class PositionTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final Position POS_A = Position.of("A");

	@Test
	public void coordinatesContainPositionThrows() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("A position is contained");
		Position.of(POS_A);
	}

	@Test
	public void assertConsistentWithCorrectSet() {
		assertConsistentDimensions(POS_A, ImmutableSet.of(String.class));
	}

	@Test
	public void assertConsistentWithWrongSetThrows() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("assignable");
		assertConsistentDimensions(POS_A, ImmutableSet.of(Integer.class));
	}

	@Test
	public void positionOfSameTypeAndNonEqualThrow() {
		expectUniqueDimensionsException();
		Position.of("A", "B");
	}

	@Test
	public void positionOfSetSameTypeThrow() {
		expectUniqueDimensionsException();
		Position.of(ImmutableSet.of("A", "B"));
	}

	@Test
	public void positionOfSameTypeAndSameValueThrow() {
		expectUniqueDimensionsException();
		Position.of("A", "A");
	}

	private void expectUniqueDimensionsException() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("unique dimensions");
	}

}
