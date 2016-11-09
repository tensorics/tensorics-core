/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

public class OngoingDetectionDirectionAware<T extends Comparable<T>> {

    private Class<T> clazz;
    private Tensor<Boolean> tensor;

    /**
     * @param clazzToReturn
     * @param tensor
     */
    public OngoingDetectionDirectionAware(Class<T> clazzToReturn, Tensor<Boolean> tensor) {
        this.tensor = tensor;
        this.clazz = clazzToReturn;
        Shape shape = tensor.shape();

        if (shape.dimensionSet().size() > 1) {
            throw new IllegalArgumentException("Provided tensor has more than 1 dimension.");
        }
        if (tensor.shape().size() == 0) {
            throw new IllegalArgumentException("Provided tensor " + tensor.shape().dimensionSet() + " is empty!");
        }
        if (!shape.dimensionSet().contains(clazz)) {
            throw new IllegalArgumentException("Provided tensor " + tensor.shape().dimensionSet()
                    + " doesn't have requested detection direction class [" + clazz + "]");
        }
    }
    


    public Iterable<T> changes() {
        /* TODO what with this logic. Should that be a separate class? I guess so... */
        Shape shape = tensor.shape();
        List<T> changes1 = new ArrayList<T>();
        List<T> coordinatesOfType = new ArrayList<>(shape.coordinatesOfType(clazz));
        Collections.sort(coordinatesOfType);
        Boolean startValue = tensor.get(coordinatesOfType.get(0));
        /* primitive scan of the evolution, marks only XOR values. TODO: include minimum margin to evaluate change */
        for (T one : coordinatesOfType) {
            Boolean newValue = tensor.get(one);
            if (startValue ^ newValue) {
                changes1.add(one);
            }
            startValue = newValue;
        }
        return changes1;
    }

}
