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
package org.tensorics.core.resolve;

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.lang.Tensorics.at;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.tensorics.core.lang.DoubleScript;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.orbit.MultibeamOrbit;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.OrbitCoordinate;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class DeferredTensorBackedCalculationTest {

	private ResolvingEngine engine;
	private MultibeamOrbit multibeamObject;

	@Before
	public void setUp() {
		engine = ResolvingEngines.defaultEngine();
		multibeamObject = new MultibeamOrbit(prepareDoubleTensor(1.0));
	}

	@Test
	public void testResolveResolvedExpression() {
		assertEquals(multibeamObject, engine.resolve(ResolvedExpression.of(multibeamObject)));
	}

	@Ignore("TestForKajetan")
	@Test
	public void testMoreComplicatedScript() throws Exception {
		MultibeamOrbit result = engine.resolve(new DoubleScript<MultibeamOrbit>() {
			@Override
			protected Expression<MultibeamOrbit> describe() {
				// Expression<MultibeamOrbit> resolved =
				// ResolvedExpression.of(multibeamObject);
				// return calculateTB(resolved).elementDividedByT(resolved);
				return null;
			}
		});
		assertEquals(1, result.getValueAt("name 1", Beam.B1, Plane.H), 0.00001);
	}

	private Tensor<Double> prepareDoubleTensor(double factor) {
		Builder<Double> builder = ImmutableTensor.builder(getDimensions());
		for (int i = 0; i < 10; i++) {
			builder.put(at(createCoordinates(i) ), (factor * i));
		}
		return builder.build();
	}

	private Set<OrbitCoordinate> createCoordinates(int i) {
		Set<OrbitCoordinate> coor = new HashSet<>();
		coor.add(i % 2 == 0 ? Plane.V : Plane.H);
		coor.add(i % 3 == 0 ? Beam.B1 : Beam.B2);
		coor.add(new Bpm("name " + i));
		return coor;
	}

	private Set<Class<?>> getDimensions() {
		Set<Class<?>> dimensions = new HashSet<>();
		dimensions.add(Bpm.class);
		dimensions.add(Beam.class);
		dimensions.add(Plane.class);
		return dimensions;
	}

}
