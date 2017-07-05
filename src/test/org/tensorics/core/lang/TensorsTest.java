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
import static org.tensorics.core.fields.doubles.Structures.doubles;
import static org.tensorics.core.tensorbacked.Tensorbackeds.construct;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorStructurals;

import com.google.common.collect.ImmutableSet;

public class TensorsTest {

    private Tensor<Double> tensor;

    @Before
    public void setUp() throws Exception {
        tensor = ImmutableTensor.<Double> builder(ImmutableSet.of(XCoordinate.class, YCoordinate.class)).build();
    }

    @Test
    public void testTensorReduction() {
        Tensor<Double> reduced = TensorStructurals.from(tensor).reduce(XCoordinate.class).byAveragingIn(doubles());
        assertEquals(2, tensor.shape().dimensionSet().size());
        assertEquals(1, reduced.shape().dimensionSet().size());
    }

    @Test
    public void testTensorReductionInField() {
        Tensor<Double> reduced = TensorStructurals.from(tensor).reduce(XCoordinate.class).byAveragingIn(doubles());
        assertEquals(2, tensor.shape().dimensionSet().size());
        assertEquals(1, reduced.shape().dimensionSet().size());
    }

    @Test
    public void testTensorMerge() {
        Tensor<Double> tensor1 = prepareTensorWithContextOf(ImmutableSet.of(ZCoordinate.of(1)));
        Tensor<Double> tensor2 = prepareTensorWithContextOf(ImmutableSet.of(ZCoordinate.of(3)));
        assertEquals(2, tensor1.shape().dimensionSet().size());
        assertEquals(2, tensor2.shape().dimensionSet().size());
        Set<Tensor<Double>> toMerge = new HashSet<>();
        toMerge.add(tensor1);
        toMerge.add(tensor2);
        Tensor<Double> merged = TensorStructurals.merge(toMerge);
        Shape shapeOfMerged = merged.shape();
        assertEquals(3, shapeOfMerged.dimensionality());
    }

    @Test(expected = IllegalStateException.class)
    public void testTensorMergeFailsDueToWrongContextContent() {
        prepareTensorWithContextOf(ImmutableSet.of(ZCoordinate.of(1)));
        prepareTensorWithContextOf(ImmutableSet.of(YCoordinate.of(2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTensorMergeFailsDueNoContextProvided() {
        Tensor<Double> tensor1 = prepareTensorWithContextOf(ImmutableSet.of(ZCoordinate.of(1)));
        Tensor<Double> tensor2 = prepareTensorWithContextOf(Collections.emptySet());
        Set<Tensor<Double>> toMerge = new HashSet<>();
        toMerge.add(tensor1);
        toMerge.add(tensor2);
        TensorStructurals.merge(toMerge);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTensorMergeFailsDueToDifferentContext() {
        Tensor<Double> tensor1 = prepareTensorWithContextOf(ImmutableSet.of(ZCoordinate.of(1)));
        Tensor<Double> tensor2 = prepareTensorWithContextOf(ImmutableSet.of(TCoordinate.of(3)));
        Set<Tensor<Double>> toMerge = new HashSet<>();
        toMerge.add(tensor1);
        toMerge.add(tensor2);
        assertEquals(2, tensor1.shape().dimensionSet().size());
        assertEquals(2, tensor2.shape().dimensionSet().size());

        TensorStructurals.merge(toMerge);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTensorMergeFailsDueToOnlyOneTensor() {
        Tensor<Double> tensor1 = prepareTensorWithContextOf(ImmutableSet.of(ZCoordinate.of(1)));
        Set<Tensor<Double>> toMerge = new HashSet<>();
        toMerge.add(tensor1);
        assertEquals(2, tensor1.shape().dimensionSet().size());
        TensorStructurals.merge(toMerge);
    }

    @Test
    public void testTensorbackedMerge() {
        Tensor<Double> tensor1 = prepareTensorWithContextOf(ImmutableSet.of(ZCoordinate.of(1)));
        Tensor<Double> tensor2 = prepareTensorWithContextOf(ImmutableSet.of(ZCoordinate.of(3)));
        Set<TensorBackedTwoCoordinates> toMerge = new HashSet<>();
        toMerge.add(new TensorBackedTwoCoordinates(tensor1));
        toMerge.add(new TensorBackedTwoCoordinates(tensor2));
        TensorBackedThreeCoordinates merged = construct(TensorBackedThreeCoordinates.class).byMergingTb(toMerge);
        Shape shapeOfMerged = merged.tensor().shape();
        assertEquals(3, shapeOfMerged.dimensionality());
    }

    private Tensor<Double> prepareTensorWithContextOf(Set<?> coordinateForContext) {
        Builder<Double> tensorBuilder = ImmutableTensor
                .<Double> builder(ImmutableSet.of(XCoordinate.class, YCoordinate.class));
        if (coordinateForContext.size() > 0) {
            tensorBuilder.context(Position.of(coordinateForContext));
        }
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                Object[] coordinates = { YCoordinate.of(j), XCoordinate.of(i) };
                tensorBuilder.put(Tensorics.at(coordinates), ((double) i * j));
            }
        }
        return tensorBuilder.build();
    }
}
