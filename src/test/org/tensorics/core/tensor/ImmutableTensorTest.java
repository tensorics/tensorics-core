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

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableTensor.Builder;

import com.google.common.collect.ImmutableSet;

public class ImmutableTensorTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testDoubleToDoubleTensor() {
		Builder<Double> tensorBuilder = ImmutableTensor.builder(Collections.singleton(Double.class));
		Tensor<Double> tensor = tensorBuilder.build();
		assertEquals(1, tensor.shape().dimensionality());
	}

	@Test
	public void testNumber() {
		Builder<Double> tensorBuilder = ImmutableTensor.builder(ImmutableSet.of(Double.class, Integer.class));
		Tensor<Double> tensor = tensorBuilder.build();
		assertEquals(2, tensor.shape().dimensionality());
	}

	@Test
	public void testZeroDimensionTensor() {
		Tensor<Double> asZeroDimension = Tensorics.zeroDimensionalOf(2.4);
		assertEquals(2.4, asZeroDimension.get(), 0.0);
		assertEquals(0, asZeroDimension.shape().dimensionality());
		assertEquals(1, asZeroDimension.shape().size());
	}

	@Test
	public void sameDimensionTwiceThrowsImmediately() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("unique");
		Tensorics.builder(Integer.class, Integer.class);
	}

	

}
