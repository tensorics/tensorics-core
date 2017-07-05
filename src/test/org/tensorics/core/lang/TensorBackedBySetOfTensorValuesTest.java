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

package org.tensorics.core.lang;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorStructurals;

import com.google.common.collect.ImmutableSet;

/**
 * @author agorzaws
 */
public class TensorBackedBySetOfTensorValuesTest {

    private static final double DOUBLE_LIMIT = 0.001;
    private static final int TENSOR_10_x_10_ORDER_2 = 2;
    private Tensor<Double> tensorToTest;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tensorToTest = createTensor();
    }

    @Test
    public void orderOfTensor() {
        assertEquals(TENSOR_10_x_10_ORDER_2, tensorToTest.shape().dimensionSet().size());
    }

    @Test
    public void valueOfTensor() {
        XCoordinate x = XCoordinate.of(8);
        YCoordinate y = YCoordinate.of(8);
        assertEquals(64.0, tensorToTest.get(x, y), DOUBLE_LIMIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfTensorWithNotEnoughCoordinates() {
        YCoordinate y = YCoordinate.of(8);
        assertEquals(64.0, tensorToTest.get(y), DOUBLE_LIMIT);
    }

    @Test
    public void orderOfSliceOfTensor() {
        XCoordinate x = XCoordinate.of(8);
        Tensor<Double> tensorOnXCoordinate = TensorStructurals.from(tensorToTest).extract(x);
        assertEquals(tensorToTest.shape().dimensionSet().size() - 1, tensorOnXCoordinate.shape().dimensionSet().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void sliceOfTensorGetSameCoordinate() {
        XCoordinate x = XCoordinate.of(8);
        Tensor<Double> tensorOnXCoordinate = TensorStructurals.from(tensorToTest).extract(x);
        assertEquals(1.0, tensorOnXCoordinate.get(x), DOUBLE_LIMIT);
    }

    @Test
    public void sliceOfTensorGetValue() {
        XCoordinate x = XCoordinate.of(8);
        YCoordinate y = YCoordinate.of(8);
        Tensor<Double> tensorOnXCoordinate = TensorStructurals.from(tensorToTest).extract(x);
        assertEquals(64.0, tensorOnXCoordinate.get(y), DOUBLE_LIMIT);
    }

    private Tensor<Double> createTensor() {
        ImmutableSet<Class<?>> dimensions = ImmutableSet.of(XCoordinate.class, YCoordinate.class);
        Builder<Double> builder = ImmutableTensor.builder(dimensions);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                builder.put(Position.of(coordinatesFor(i, j)), valueFor(i, j));
            }
        }
        return builder.build();
    }

    private double valueFor(int i, int j) {
        return j * i * 1.0;
    }

    private Set<TestCoordinate> coordinatesFor(int i, int j) {
        Set<TestCoordinate> coordinates = new HashSet<>();
        coordinates.add(XCoordinate.of(i));
        coordinates.add(YCoordinate.of(j));
        return coordinates;
    }

}
