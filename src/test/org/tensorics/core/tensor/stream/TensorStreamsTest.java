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

package org.tensorics.core.tensor.stream;

import static java.util.Collections.emptySet;
import static org.junit.Assert.assertEquals;
import static org.tensorics.core.tensor.stream.TensorStreamFilters.byValue;
import static org.tensorics.core.tensor.stream.TensorStreamMappers.coordinatesOfType;
import static org.tensorics.core.tensor.stream.TensorStreamMappers.values;
import static org.tensorics.core.tensor.stream.TensorStreams.toTensor;
import static org.tensorics.core.tensor.stream.TensorStreams.toTensorbacked;

import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.AbstractTensorbacked;
import org.tensorics.core.tensorbacked.annotation.Dimensions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class TensorStreamsTest {

	private enum TestCoordinate {
		FIRST_VALUE, SECOND_VALUE, THIRD_VALUE
	}

	@Dimensions({ TestCoordinate.class, Double.class })
	private static class SomeDomainObject extends AbstractTensorbacked<Double> {
		private static final long serialVersionUID = 1L;

		public SomeDomainObject(Tensor<Double> tensor) {
			super(tensor);
		}
	}

	private Tensor<Double> tensor;

	@Rule
	public ExpectedException thrown = ExpectedException.none();


    @Before
    public void prepareTensor() {
        Builder<Double> tensorBuilder = ImmutableTensor.builder(ImmutableSet.of(Double.class, Integer.class));
        tensorBuilder.put(Position.of(0.0, 0), 0.0);
        tensorBuilder.put(Position.of(23.0, 1), 42.42);
        tensorBuilder.put(Position.of(23.0, 2), 2.0);
        tensor = tensorBuilder.build();
    }

	@Test
	public void test() {
		Tensor<Double> mapped = Tensorics.stream(tensor).map(values(e -> e + 1)).filter(byValue(e -> e > 5))
				.collect(toTensor(ImmutableSet.of(Double.class, Integer.class)));

		assertEquals(1, mapped.shape().size());
		assertEquals(43.42, mapped.get(23.0, 1), 1e-6);
	}

	@Test
	public void convertTensorToDomainObjectByStreams() {
		SomeDomainObject domainObject = Tensorics.stream(tensor)
				.map(coordinatesOfType(Integer.class, i -> TestCoordinate.values()[i]))
				.collect(toTensorbacked(SomeDomainObject.class));

		assertEquals(0.0, domainObject.tensor().get(0.0, TestCoordinate.FIRST_VALUE), 1e-6);
		assertEquals(42.42, domainObject.tensor().get(23.0, TestCoordinate.SECOND_VALUE), 1e-6);
		assertEquals(2.0, domainObject.tensor().get(23.0, TestCoordinate.THIRD_VALUE), 1e-6);
	}

	@Test
	public void duplicatePositionThrows() {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("duplicate entry");

		ImmutableList.of(1, 2, 3).stream().collect(toTensor(any -> Position.empty(), v -> v, emptySet()));
	}

	@Test
	public void inconsistentPositionThrows() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("not assignable");
		Position positions[] = new Position[] { Position.of(1), Position.of(42.0), Position.of("fail") };
		ImmutableList.of(0, 1, 2).stream()
				.collect(toTensor(i -> positions[i], v -> v, Collections.singleton(Integer.class)));
	}

}
