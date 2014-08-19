/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples;

import cern.mpe.commons.tensorics.domain.tensor.annotation.Dimensions;
import cern.mpe.commons.tensorics.domain.tensor.backed.orbit.coordinates.Beam;
import cern.mpe.commons.tensorics.domain.tensor.backed.orbit.coordinates.Bpm;
import cern.mpe.commons.tensorics.domain.tensor.backed.orbit.coordinates.Plane;
import cern.mpe.commons.tensorics.domain.tensor.base.Tensor;
import cern.mpe.commons.tensorics.domain.tensor.base.TensorBacked;

/**
 * Test case. Multibeam class supported with TensorBacked(Double)
 * 
 * @author agorzaws
 */
//tag::classdef[]
@Dimensions({ Beam.class, Plane.class, Bpm.class })
public class MultibeamOrbit implements TensorBacked<Double> {

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
//end::classdef[]

