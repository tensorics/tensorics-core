/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.orbit;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;

@Dimensions({ Plane.class, Bpm.class })
public class SinglebeamOrbit extends AbstractOrbit {

    public SinglebeamOrbit(Tensor<Double> tensor) {
        super(tensor);
    }

}
