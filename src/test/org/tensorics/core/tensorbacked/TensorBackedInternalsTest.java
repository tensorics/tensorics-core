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

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.tensorbacked.TensorbackedInternals.dimensionsOf;

import org.junit.Test;
import org.tensorics.core.tensorbacked.orbit.MultibeamOrbit;
import org.tensorics.core.tensorbacked.orbit.MultibeamOrbitEvolution;
import org.tensorics.core.tensorbacked.orbit.SinglebeamOrbit;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;
import org.tensorics.core.tensorbacked.orbit.coordinates.Time;

import com.google.common.collect.ImmutableSet;

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

    @Test(expected = IllegalArgumentException.class)
    public void testNoAnnotationPresent() {
        dimensionsOf(NoAnnotationPresentOrbit.class);
    }

}
