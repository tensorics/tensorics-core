/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import org.tensorics.core.booleans.operations.TensorChangeDetectionOperation;
import org.tensorics.core.tensor.Tensor;

/**
 * @author agorzaws
 */
public class OngoingDetection {

    private final Tensor<Boolean> tensor;

    public OngoingDetection(Tensor<Boolean> tensor) {
        this.tensor = requireNonNull(tensor, "tensor must not be null");
    }

    /**
     * Defines the direction of the tensor in which the searching of the changes will be performed.
     * <p>
     * This class must implement {@link Comparable}.
     * 
     * @param direction the dimension in which direction to search
     * @return
     */
    public <C extends Comparable<C>> Iterable<C> changesAlong(Class<C> direction) {
        return TensorChangeDetectionOperation.of(direction).apply(tensor);
    }

    public <C> Iterable<C> changesAlong(Class<C> direction, Comparator<C> comparator) {
        return TensorChangeDetectionOperation.of(direction, comparator).apply(tensor);
    }

}
