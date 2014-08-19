/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.orbit;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.AbstractTensorbacked;

public abstract class AbstractOrbit extends AbstractTensorbacked<Double> {

    public AbstractOrbit(Tensor<Double> tensor) {
        super(tensor);
    }

}
