/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.orbit;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.AbstractTensorbacked;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;

/**
 * Test case. Multibeam class supported with TensorBacked(QuantifiedValue(Double))
 * 
 * @author agorzaws
 */
@Dimensions({ Beam.class, Plane.class, Bpm.class })
public class MultibeamQOrbit extends AbstractTensorbacked<QuantifiedValue<Double>> {

    /**
     * @param tensor
     */
    public MultibeamQOrbit(Tensor<QuantifiedValue<Double>> tensor) {
        super(tensor);
    }

    public QuantifiedValue<Double> getValueAt(String string, Beam b, Plane p) {
        return backingTensor.get(b, p, new Bpm(string));
    }

}
