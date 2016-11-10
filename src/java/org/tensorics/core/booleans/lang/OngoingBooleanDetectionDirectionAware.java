/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import org.tensorics.core.tensor.Tensor;

public class OngoingBooleanDetectionDirectionAware<T extends Comparable<T>> {

    private Class<T> clazzToReturn;

    public OngoingBooleanDetectionDirectionAware(Class<T> clazz) {
        this.clazzToReturn = clazz;
    }

    public OngoingDetectionDirectionAndTensorAware<T> where(Tensor<Boolean> tensor) {
        return new OngoingDetectionDirectionAndTensorAware<T>(clazzToReturn, tensor);
    }
    
    
    
}
