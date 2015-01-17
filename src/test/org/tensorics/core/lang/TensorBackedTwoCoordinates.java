/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.AbstractTensorbacked;
import org.tensorics.core.tensorbacked.annotation.Dimensions;

@Dimensions({ XCoordinate.class, YCoordinate.class })
public class TensorBackedTwoCoordinates extends AbstractTensorbacked<Double> {

    public TensorBackedTwoCoordinates(Tensor<Double> tensor) {
        super(tensor);
    }

}
