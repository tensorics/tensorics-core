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
package org.tensorics.core.tensor.lang;

import static com.google.common.collect.ImmutableSet.of;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorPair;
import org.tensorics.core.tensor.options.BroadcastMissingDimensionsStrategy;
import org.tensorics.core.tensor.options.BroadcastingStrategy;

public class BroadcastAllBroadcastingStrategyTest {

    private static final Tensor<Double> ZERO_DIMENSIONAL_ZERO = Tensorics.zeroDimensionalOf(0.0);

    private static final Position POS_A = Position.of("A");
    private static final Position POS_B = Position.of("B");
    private static final Position POS_1 = Position.of(1);
    private static final Position POS_2 = Position.of(2);

    private static final Position POS_A1 = Position.of("A", 1);
    private static final Position POS_A2 = Position.of("A", 2);
    private static final Position POS_B1 = Position.of("B", 1);
    private static final Position POS_B2 = Position.of("B", 2);

    private static final Shape FULL_SHAPE = Shape.of(of(String.class, Integer.class), POS_A1, POS_A2, POS_B1, POS_B2);

    private BroadcastingStrategy strategy;

    @Before
    public void setUp() {
        strategy = new BroadcastMissingDimensionsStrategy();
    }

    @Test
    public void markerInterfaceIsBroadcastingStrategy() {
        assertEquals(BroadcastingStrategy.class, strategy.getMarkerInterface());
    }

    @Test(expected = NullPointerException.class)
    public void nullLeftThrows() {
        strategy.broadcast(null, ZERO_DIMENSIONAL_ZERO, emptyDimensions());
    }

    private Set<Class<?>> emptyDimensions() {
        return Collections.<Class<?>> emptySet();
    }

    @Test(expected = NullPointerException.class)
    public void nullRightThrows() {
        strategy.broadcast(null, ZERO_DIMENSIONAL_ZERO, emptyDimensions());
    }

    @Test
    public void zeroDimensionalToZeroDimensional() {
        TensorPair<Double> result = strategy.broadcast(ZERO_DIMENSIONAL_ZERO, ZERO_DIMENSIONAL_ZERO, emptyDimensions());
        assertEquals(ZERO_DIMENSIONAL_ZERO, Tensorics.copyOf(result.left()));
        assertEquals(ZERO_DIMENSIONAL_ZERO, Tensorics.copyOf(result.right()));
    }

    @Test
    public void zeroDimensionalBroadcastedToAB() {
        Tensor<Double> tensor = createAB();
        TensorPair<Double> result = strategy.broadcast(ZERO_DIMENSIONAL_ZERO, tensor, emptyDimensions());
        assertEquals(tensor, Tensorics.copyOf(result.right()));

        Tensor<Double> left = result.left();
        assertEquals(Shape.of(of(String.class), POS_A, POS_B), left.shape());
        assertEquals(0.0, left.get(POS_A), 0.000001);
        assertEquals(0.0, left.get(POS_B), 0.000001);
    }

    @Test
    public void broadcastBoth() {
        Tensor<Double> tensorAB = createAB();
        Tensor<Double> tensor12 = create12();

        TensorPair<Double> result = strategy.broadcast(tensorAB, tensor12, emptyDimensions());
        Tensor<Double> left = result.left();
        Tensor<Double> right = result.right();
        assertEquals(FULL_SHAPE, left.shape());
        assertEquals(FULL_SHAPE, right.shape());

        /* AB-tensor values are broadcasted in Integer - direction */
        assertValue(left, POS_A1, 1.0);
        assertValue(left, POS_A2, 1.0);
        assertValue(left, POS_B1, 2.0);
        assertValue(left, POS_B2, 2.0);

        /* 12-tensor values are broadcasted in String - direction */
        assertValue(right, POS_A1, 0.1);
        assertValue(right, POS_A2, 0.2);
        assertValue(right, POS_B1, 0.1);
        assertValue(right, POS_B2, 0.2);

    }

    private void assertValue(Tensor<Double> tensor, Position pos, double value) {
        assertEquals(value, tensor.get(pos), 0.000001);
    }

    private Tensor<Double> createAB() {
        ImmutableTensor.Builder<Double> builder = ImmutableTensor.builder(String.class);
        builder.at(POS_A).put(1.0);
        builder.at(POS_B).put(2.0);
        return builder.build();
    }

    private Tensor<Double> create12() {
        ImmutableTensor.Builder<Double> builder = ImmutableTensor.builder(Integer.class);
        builder.at(POS_1).put(0.1);
        builder.at(POS_2).put(0.2);
        return builder.build();
    }

}
