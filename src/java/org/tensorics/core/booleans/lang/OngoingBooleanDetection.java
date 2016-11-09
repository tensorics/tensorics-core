/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import org.tensorics.core.tensor.Tensor;

public class OngoingBooleanDetection<T extends Comparable<T>> {

    private Class<T> clazzToReturn;

    public OngoingBooleanDetection(Class<T> clazz) {
        this.clazzToReturn = clazz;
    }

    public OngoingDetectionDirectionAware<T> where(Tensor<Boolean> tensor) {
        return new OngoingDetectionDirectionAware<T>(clazzToReturn, tensor);
    }    
}
