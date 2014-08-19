/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.orbit;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;

/**
 * Test case. Multibeam class supported with TensorBacked(Double)
 * 
 * @author agorzaws
 */
@Dimensions({ Beam.class, Plane.class, Bpm.class })
public class MultibeamOrbit extends AbstractOrbit {

    public MultibeamOrbit(Tensor<Double> tensor) {
        super(tensor);
    }

    public double getValueAt(String string, Beam b2, Plane h) {
        return backingTensor.get(b2, h, new Bpm(string));
    }

}
