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

import static org.assertj.core.api.Assertions.assertThat;
import static org.tensorics.core.testing.TestUtil.assertUtilityClass;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class CoordinatesTest {

	@Test
	public void verifyUtiliyClass() {
		assertUtilityClass(Coordinates.class);
	}

	@Test
	public void checkRelationsAllowed() {
		Set<Class<?>> coordinates = new HashSet<>();
		coordinates.add(IA.class);
		coordinates.add(IB.class);
		Coordinates.checkClassRelations(A.class, coordinates);
	}

	@Test
	public void checkRelationsAllowed2() {
		Set<Class<?>> coordinates = new HashSet<>();
		coordinates.add(IA.class);
		coordinates.add(IC.class);
		Coordinates.checkClassRelations(C.class, coordinates);
	}

	@Test
	public void checkRelationsAllowed3() {
		Set<Class<?>> coordinates = new HashSet<>();
		coordinates.add(IA.class);
		coordinates.add(IB.class);
		Coordinates.checkClassRelations(C.class, coordinates);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkRelationsNotAllowed() {
		Set<Class<?>> coordinates = new HashSet<>();
		coordinates.add(IA.class);
		coordinates.add(IC.class);
		Coordinates.checkClassRelations(B.class, coordinates);
	}

	@Test
	public void testCoordinatesBunch1() {
		Set<Class<?>> coordinates = new HashSet<>();
		coordinates.add(IA.class);
		coordinates.add(IC.class);
		Coordinates.checkClassesRelations(coordinates);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCoordinatesBunch2() {
		Set<Class<?>> coordinates = new HashSet<>();
		coordinates.add(IA.class);
		coordinates.add(IB.class);
		coordinates.add(IC.class);
		Coordinates.checkClassesRelations(coordinates);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCoordinatesBunchNotAllowed() {
		Set<Class<?>> coordinates = new HashSet<>();
		coordinates.add(IA.class);
		coordinates.add(IB.class);
		coordinates.add(IE.class);
		Coordinates.checkClassesRelations(coordinates);
	}

	@Test
	public void parentClassIntersection() {
		ImmutableSet<Class<?>> leftCoordinates = ImmutableSet.of(A.class, IB.class);
		ImmutableSet<Class<?>> rightCoordinates = ImmutableSet.of(IA.class, B.class);
		Set<Class<?>> result = Coordinates.parentClassIntersection(leftCoordinates, rightCoordinates);
		assertThat(result).containsExactlyInAnyOrder(IA.class, IB.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parentClassObjectIntersection() {
		ImmutableSet<Class<?>> leftCoordinates = ImmutableSet.of(A.class, B.class);
		ImmutableSet<Class<?>> rightCoordinates = ImmutableSet.of(Object.class);
		Coordinates.parentClassIntersection(leftCoordinates, rightCoordinates);
	}

	@Test
	public void parentClassIntersectionDifferentSets() {
		ImmutableSet<Class<?>> leftCoordinates = ImmutableSet.of(A.class, B.class);
		ImmutableSet<Class<?>> rightCoordinates = ImmutableSet.of(C.class);
		Set<Class<?>> result = Coordinates.parentClassIntersection(leftCoordinates, rightCoordinates);
		assertThat(result).isEmpty();
	}

	interface IA {
		/* nothing to do */
	}

	interface IB {
		/* nothing to do */
	}

	interface IC extends IB {
		/* nothing to do */
	}

	interface ID {
		/* nothing to do */
	}

	interface IE extends IA, ID {
		/* nothing to do */
	}

	class A implements IA {
		/* nothing to do */
	}

	class B implements IB {
		/* nothing to do */
	}

	class C implements IC {
		/* nothing to do */
	}
}
