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

package org.tensorics.core.tensorbacked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tensorics.core.tensorbacked.TensorbackedInternals.dimensionsOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import com.google.common.reflect.TypeToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.dimtyped.Tensorbacked2d;
import org.tensorics.core.tensorbacked.interfacebacked.DimtypedAndAnnotationPresentOrbit;
import org.tensorics.core.tensorbacked.interfacebacked.SingleBeamOrbitIf;
import org.tensorics.core.tensorbacked.orbit.MultibeamOrbit;
import org.tensorics.core.tensorbacked.orbit.MultibeamOrbitEvolution;
import org.tensorics.core.tensorbacked.orbit.SinglebeamOrbit;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;
import org.tensorics.core.tensorbacked.orbit.coordinates.Time;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Set;

public class TensorBackedInternalsTest {

    @Test
    public void testDimensionsOfMultibeamOrbitEvolution() {
        assertEquals(ImmutableSet.of(Plane.class, Bpm.class, Time.class, Beam.class),
                dimensionsOf(MultibeamOrbitEvolution.class));
    }

    @Test
    public void testDimensionsOfMultibeamOrbit() {
        assertEquals(ImmutableSet.of(Plane.class, Bpm.class, Beam.class), dimensionsOf(MultibeamOrbit.class));
    }

    @Test
    public void testDimensionsOfSinglebeamOrbit() {
        assertEquals(ImmutableSet.of(Plane.class, Bpm.class), dimensionsOf(SinglebeamOrbit.class));
    }

    @Test
    public void testNoAnnotationPresent() {
        assertThrows(IllegalArgumentException.class, () -> dimensionsOf(NoAnnotationPresentOrbit.class));
    }

    @Test
    public void dimensionsFromDimtypedAreRetrieved() {
        Set<Class<?>> dimensions = dimensionsOf(SingleBeamOrbitIf.class);
        Assertions.assertThat(dimensions).containsExactlyInAnyOrder(Bpm.class, Plane.class);
    }

    @Test
    public void whenDimtypedIsImplementedThenNoAnnotationMustBePresent() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dimensionsOf(DimtypedAndAnnotationPresentOrbit.class));
        assertThat(exception.getMessage(), containsString("No annotation of type 'interface org.tensorics.core.tensorbacked.annotation.Dimensions' must be present"));
    }

    @Test
    public void createBackedByTensorWorksForConstructorBasedInstantiation() {
        SinglebeamOrbit singleBeamOrbit = TensorbackedInternals.createBackedByTensor(SinglebeamOrbit.class, anyBpmPlaneTensor());
        Assertions.assertThat(singleBeamOrbit.tensor().shape().size()).isEqualTo(1);
    }

    @Test
    public void createBackedByTensorWorksForInterfaceProxyingInstantiation() {
        SingleBeamOrbitIf singleBeamOrbit = TensorbackedInternals.createBackedByTensor(SingleBeamOrbitIf.class, anyBpmPlaneTensor());
        Assertions.assertThat(singleBeamOrbit.tensor().shape().size()).isEqualTo(1);
    }


    @Test
    public void checkDimensionsFailsForWrongDimensions() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> TensorbackedInternals.verifyDimensions(SinglebeamOrbit.class, anyPlaneTensor()));
        assertThat(exception.getMessage(), containsString("do not match dimensions"));
    }

    /**
     * At the moment, 'compatible dimensions' are ok. However, why can't we be stricter here? I.e. the tensor should have the exact same dimensions
     * as required by the tensorbacked (in principle enforced by the builder, or not?....)
     */
    @Test
    public void checkDimensionsFailsForCompatibleClasses() {
        TensorbackedInternals.verifyDimensions(SinglebeamOrbit.class, anyInheritedBpmPlaneTensor());
    }


    @Test
    public void inconsistentDimensionThrowsForConstructorInstantiation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> TensorbackedInternals.createBackedByTensor(SinglebeamOrbit.class, anyPlaneTensor()));
        assertThat(exception.getMessage(), containsString("do not match dimensions of given tensor"));
    }

    @Test
    public void inconsistentDimensionThrowsForProxiedInterfaceInstantiation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> TensorbackedInternals.createBackedByTensor(SingleBeamOrbitIf.class, anyPlaneTensor()));
        assertThat(exception.getMessage(), containsString("do not match dimensions of given tensor"));
    }


    private Tensor<Double> anyBpmPlaneTensor() {
        return ImmutableTensor.<Double>builder(Bpm.class, Plane.class).put(Position.of(new Bpm("BPM1"), Plane.H), 1.0).build();
    }

    private Tensor<Double> anyPlaneTensor() {
        return ImmutableTensor.<Double>builder(Plane.class).put(Position.of(Plane.H), 1.0).build();
    }

    private Tensor<Double> anyInheritedBpmPlaneTensor() {
        return ImmutableTensor.<Double>builder(InheritedBpm.class, Plane.class).put(Position.of(new InheritedBpm("BPM1"), Plane.H), 1.0).build();
    }


    private static class InheritedBpm extends Bpm {

        public InheritedBpm(String name) {
            super(name);
        }
    }
}
