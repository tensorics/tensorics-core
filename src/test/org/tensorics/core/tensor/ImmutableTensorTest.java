/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableTensor.Builder;

import com.google.common.collect.ImmutableSet;

public class ImmutableTensorTest {

    @Test
    public void testDoubleToDoubleTensor() {
        Builder<Double> tensorBuilder = Tensorics.builder(Collections.singleton(Double.class));
        Tensor<Double> tensor = tensorBuilder.build();
        assertEquals(1, tensor.shape().dimensionality());
    }

    @Test
    public void testNumber() {
        Builder<Double> tensorBuilder = Tensorics.builder(ImmutableSet.of(Double.class, Integer.class));
        Tensor<Double> tensor = tensorBuilder.build();
        assertEquals(2, tensor.shape().dimensionality());
    }

    @Test
    public void testZeroDimensionTensor() {
        Tensor<Double> asZeroDimension = Tensorics.zeroDimensionalOf(2.4);
        assertEquals(2.4, asZeroDimension.get(), 0.0);
        assertEquals(0, asZeroDimension.shape().dimensionality());
        assertEquals(1, asZeroDimension.shape().size());
    }

}
