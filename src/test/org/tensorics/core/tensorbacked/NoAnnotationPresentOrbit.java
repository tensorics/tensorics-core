/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.orbit.AbstractOrbit;

public class NoAnnotationPresentOrbit extends AbstractOrbit {

    public NoAnnotationPresentOrbit(Tensor<Double> tensor) {
        super(tensor);
    }

}
