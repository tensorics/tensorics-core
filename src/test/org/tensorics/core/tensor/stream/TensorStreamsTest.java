/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.stream;

import static org.junit.Assert.*;
import static org.tensorics.core.tensor.stream.TensorStreamFilters.byValue;
import static org.tensorics.core.tensor.stream.TensorStreams.toTensor;
import static org.tensorics.core.tensor.stream.TensorStreamMappers.values;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;

import com.google.common.collect.ImmutableSet;

public class TensorStreamsTest {

    Tensor<Double> tensor;

    @Before
    public void prepareTensor() {
        Builder<Double> tensorBuilder = ImmutableTensor.builder(ImmutableSet.of(Double.class, Integer.class));
        tensorBuilder.putAt(0.0, Position.of(0.0, 0));
        tensorBuilder.putAt(42.42, Position.of(23.0, 42));
        tensor = tensorBuilder.build();
    }

    @Test
    public void test() {
        Tensor<Double> mapped = Tensorics.stream(tensor).map(values(e -> e + 1)).filter(byValue(e -> e > 5))
                .collect(toTensor());

        assertEquals(1, mapped.shape().size());
        assertEquals(43.42, mapped.get(23.0, 42), 1e-6);
    }

}
