/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import java.util.Set;

import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableSet;

public final class OngoingFlattening<S> {

    private final Tensor<S> tensor;

    OngoingFlattening(Tensor<S> tensor) {
        super();
        this.tensor = tensor;
    }

    public OngoingDimensionFlattening<S> inDirectionOf(Class<?> dimension) {
        return inDirectionsOf(ImmutableSet.<Class<?>> of(dimension));
    }

    public OngoingDimensionFlattening<S> inDirectionsOf(Class<?>... dimensions) {
        return inDirectionsOf(ImmutableSet.<Class<?>> copyOf(dimensions));
    }

    public OngoingDimensionFlattening<S> inDirectionsOf(Set<Class<?>> dimensions) {
        return new OngoingDimensionFlattening<S>(tensor, dimensions);
    }

    public <C extends Comparable<C>> OngoingOrderedFlattening<S, C> orderedInDirectionOf(Class<C> dimension) {
        return new OngoingOrderedFlattening<S, C>(tensor, dimension);
    }
}
