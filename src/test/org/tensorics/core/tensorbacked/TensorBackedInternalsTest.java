/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
