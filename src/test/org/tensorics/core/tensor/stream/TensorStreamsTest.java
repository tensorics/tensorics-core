/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.stream;

import static org.junit.Assert.*;
import static org.tensorics.core.tensor.stream.TensorStreamFilters.byValue;
import static org.tensorics.core.tensor.stream.TensorStreams.toTensor;
import static org.tensorics.core.tensor.stream.TensorStreams.toTensorbacked;
import static org.tensorics.core.tensor.stream.TensorStreamMappers.coordinatesOfType;
import static org.tensorics.core.tensor.stream.TensorStreamMappers.values;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.AbstractTensorbacked;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensor.ImmutableTensor.Builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class TensorStreamsTest {

    private enum TestCoordinate {
        FIRST_VALUE,
        SECOND_VALUE,
        THIRD_VALUE
    }

    @Dimensions({ TestCoordinate.class, Double.class })
    private static class SomeDomainObject extends AbstractTensorbacked<Double> {
        private static final long serialVersionUID = 1L;

        public SomeDomainObject(Tensor<Double> tensor) {
            super(tensor);
        }
    }

    private Tensor<Double> tensor;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void prepareTensor() {
        Builder<Double> tensorBuilder = ImmutableTensor.builder(ImmutableSet.of(Double.class, Integer.class));
        tensorBuilder.putAt(0.0, Position.of(0.0, 0));
        tensorBuilder.putAt(42.42, Position.of(23.0, 1));
        tensorBuilder.putAt(2.0, Position.of(23.0, 2));
        tensor = tensorBuilder.build();
    }

    @Test
    public void test() {
        Tensor<Double> mapped = Tensorics.stream(tensor).map(values(e -> e + 1)).filter(byValue(e -> e > 5))
                .collect(toTensor());

        assertEquals(1, mapped.shape().size());
        assertEquals(43.42, mapped.get(23.0, 1), 1e-6);
    }

    @Test
    public void convertTensorToDomainObjectByStreams() {
        SomeDomainObject domainObject = Tensorics.stream(tensor)
                .map(coordinatesOfType(Integer.class, i -> TestCoordinate.values()[i]))
                .collect(toTensorbacked(SomeDomainObject.class));

        assertEquals(0.0, domainObject.tensor().get(0.0, TestCoordinate.FIRST_VALUE), 1e-6);
        assertEquals(42.42, domainObject.tensor().get(23.0, TestCoordinate.SECOND_VALUE), 1e-6);
        assertEquals(2.0, domainObject.tensor().get(23.0, TestCoordinate.THIRD_VALUE), 1e-6);
    }

    @Test
    public void duplicatePositionThrows() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("duplicate entry");

        ImmutableList.of(1, 2, 3).stream().collect(toTensor(any -> Position.empty(), v -> v));
    }

    @Test
    public void inconsistentPositionThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("same dimensions");

        Position positions[] = new Position[] { Position.of(1), Position.of(42.0), Position.of("fail") };        
        ImmutableList.of(0, 1, 2).stream().collect(toTensor(i -> positions[i], v -> v));
    }

}
