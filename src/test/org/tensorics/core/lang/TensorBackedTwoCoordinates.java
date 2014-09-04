/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.annotation.Dimensions;

@Dimensions({ XCoordinate.class, YCoordinate.class })
public class TensorBackedTwoCoordinates implements Tensorbacked<Double> {

    private final Tensor<Double> tensor;

    public TensorBackedTwoCoordinates(Tensor<Double> tensor) {
        super();
        this.tensor = tensor;
    }

    @Override
    public Tensor<Double> tensor() {
        return tensor;
    }

}
