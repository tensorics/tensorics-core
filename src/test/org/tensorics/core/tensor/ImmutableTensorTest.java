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

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableTensor.Builder;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class ImmutableTensorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testMergingContextBackIntoShape() {
        Builder<Double> tensorBuilder = ImmutableTensor.builder(ImmutableSet.of(Double.class, Integer.class));
        tensorBuilder.context(Position.of("TestContext"));
        tensorBuilder.putAt(0.0, Position.of(0.0, 0));
        tensorBuilder.putAt(42.42, Position.of(23.0, 42));
        Tensor<Double> tensor = tensorBuilder.build();
        Tensor<Double> tensorWithMergedContext = Tensorics.mergeContextIntoShape(tensor);
        assertEquals(tensor, Tensorics.from(tensorWithMergedContext).extract("TestContext"));
    }

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
    public void testExtractionOfEmptyPosition() {
        Builder<Double> tensorBuilder = ImmutableTensor.builder(ImmutableSet.of(Double.class, Integer.class));
        Tensor<Double> tensor = tensorBuilder.build();
        assertEquals(tensor, Tensorics.from(tensor).extract(Position.empty()));
    }

    @Test
    public void testExtractionOfCompletePosition() {
        Builder<Double> tensorBuilder = ImmutableTensor.builder(ImmutableSet.of(Double.class, Integer.class));
        tensorBuilder.putAt(0.0, Position.of(0.0, 0));
        tensorBuilder.putAt(42.42, Position.of(23.0, 42));
        Tensor<Double> tensor = tensorBuilder.build();
        assertEquals(tensor.get(23.0, 42), Tensorics.from(tensor).extract(Position.of(23.0, 42)).get());
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

    @Test
    public void fromMapWithOneEntry() {
        Map<Position, Integer> map = ImmutableMap.of(Position.of(42), 0);
        assertEquals(Tensorics.fromMap(ImmutableSet.of(Integer.class), map).asMap(), map);
    }

    @Test
    public void fromEmptyMap() {
        Map<Position, Integer> map = ImmutableMap.of();
        assertEquals(Tensorics.fromMap(ImmutableSet.of(), map).asMap(), map);
    }

    @Test
    public void fromMapThrowsOnInconsistentDimensions() {
        Map<Position, Integer> map = ImmutableMap.of(Position.of(42), 0, Position.of("fail"), 1);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("not assignable");
        Tensorics.fromMap(ImmutableSet.of(Integer.class), map);
    }

    @Test
    public void getNonExistingElementUsingInheritanceThrowsANoSuchElementException() {
        final Object value = new Object();

        Builder<Object> builder = ImmutableTensor.builder(AnyInterface.class);
        builder.putAt(value, AnyClass.INSTANCE1);
        ImmutableTensor<Object> tensor = builder.build();

        thrown.expect(NoSuchElementException.class);
        assertEquals(tensor.get(AnyClass.INSTANCE2), value);
    }

    interface AnyInterface {
        /* just an interface */
    }

    enum AnyClass implements AnyInterface {
        INSTANCE1,
        INSTANCE2;
    }
}
