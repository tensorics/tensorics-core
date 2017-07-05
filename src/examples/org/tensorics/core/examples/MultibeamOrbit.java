/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;

/**
 * Test case. Multibeam class supported with TensorBacked(Double)
 * 
 * @author agorzaws
 */
// tag::classdef[]
@Dimensions({ Beam.class, Plane.class, Bpm.class })
public class MultibeamOrbit implements Tensorbacked<Double> {

    private final Tensor<Double> tensor;

    public MultibeamOrbit(Tensor<Double> tensor) {
        this.tensor = tensor;
    }

    public double getValueAt(String string, Beam beam, Plane plane) {
        return tensor.get(beam, plane, new Bpm(string));
    }

    @Override
    public Tensor<Double> tensor() {
        return this.tensor;
    }

}
// end::classdef[]
