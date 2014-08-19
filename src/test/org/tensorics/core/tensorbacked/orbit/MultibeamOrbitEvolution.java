/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.orbit;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;
import org.tensorics.core.tensorbacked.orbit.coordinates.Time;

@Dimensions({ Time.class, Beam.class, Plane.class, Bpm.class })
public class MultibeamOrbitEvolution extends AbstractOrbit {

    public MultibeamOrbitEvolution(Tensor<Double> tensor) {
        super(tensor);
    }

}
