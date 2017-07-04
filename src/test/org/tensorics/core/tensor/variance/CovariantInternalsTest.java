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

package org.tensorics.core.tensor.variance;

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.tensor.variance.Covariants.contravariantOf;
import static org.tensorics.core.tensor.variance.Covariants.coordinateClassFor;
import static org.tensorics.core.testing.TestUtil.assertUtilityClass;

import org.junit.Test;

public class CovariantInternalsTest {

	@Test
	public void verifyUtilityClass() {
		assertUtilityClass(Covariants.class);
	}

	@Test
	public void resolveCoordinateTypeFromClass() {
		assertEquals(String.class, coordinateClassFor(CovariantString.class));
	}

	@Test
	public void resolveCoordinateTypeFromDoubleInheritedClass() {
		assertEquals(String.class, coordinateClassFor(CovariantStringDoubleInherited.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void resolveCoordinateTypeFromGenericDoubleInheritedClass() {
		assertEquals(String.class, coordinateClassFor(CovariantDoubleInheritedString.class));
	}

	@Test
	public void contravariantOfWorksForCovariant() {
		assertEquals(String.class, contravariantOf(CovariantString.class));
	}

	@Test
	public void contravariantOfWorksForContravariant() {
		assertEquals(String.class, contravariantOf(String.class));
	}

	public class CovariantString extends Covariant<String> {

		public CovariantString(String coordinate) {
			super(coordinate);
		}
	}

	public class CovariantStringDoubleInherited extends CovariantString {

		public CovariantStringDoubleInherited(String coordinate) {
			super(coordinate);
		}
	}

	public class CovariantA<T> extends Covariant<T> {

		public CovariantA(T coordinate) {
			super(coordinate);
		}
	}

	public class CovariantDoubleInheritedString extends CovariantA<String> {

		public CovariantDoubleInheritedString(String coordinate) {
			super(coordinate);
		}
	}

}
