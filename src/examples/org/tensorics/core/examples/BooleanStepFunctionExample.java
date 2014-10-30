/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableEntry;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensor.operations.TensorInternals;

public class BooleanStepFunctionExample {

    private static final Signal PIXEL1 = Signal.of("pixel1");
    private static final Signal AMPLIFIER = Signal.of("amplifier");
    private static final Date ORIGIN = new Date(0L);
    private ImmutableTensor<Boolean> stepFunctionTensor;

    @Before
    public void setUp() {
        stepFunctionTensor = createStepFunction();
    }

    @Test
    public void testStepFunctionStartsFalse() {
        assertFalse(stepFunctionTensor.get(Position.of(ORIGIN, AMPLIFIER)));
    }

    @Test
    public void testShapeStuff() {
        Shape shape = stepFunctionTensor.shape();
        assertEquals(2, shape.dimensionality());
    }

    @Test
    public void testCalculations() {
        ImmutableTensor<Boolean> tensor = stepFunctionTensor;
        Tensor<Boolean> slicedTensor = TensorStructurals.from(tensor).reduce(Signal.class).bySlicingAt(AMPLIFIER);
        assertFalse(slicedTensor.get(ORIGIN));
        assertEquals(Position.empty(), slicedTensor.context().getPosition());

        Builder<Boolean> builder = Tensorics.builderFrom(tensor);
        builder.at(Position.of(new Date(5L), AMPLIFIER)).put(false);
        builder.at(Position.of(new Date(6L), AMPLIFIER)).put(true);

        assertEquals(true, builder.build().get(Position.of(AMPLIFIER, new Date(6L))));
        assertEquals(false, builder.build().get(Position.of(ORIGIN, AMPLIFIER)));

        ImmutableTensor<Boolean> secondTensor = builder.build();
        
        TensorStructurals.from(stepFunctionTensor);
    }

    private ImmutableTensor<Boolean> createStepFunction() {
        Builder<Boolean> stepFunctionBuilder = Tensorics.builder(Date.class, Signal.class);
        stepFunctionBuilder.at(ORIGIN, AMPLIFIER).put(false);
        stepFunctionBuilder.put(new ImmutableEntry<Boolean>(Position.of(PIXEL1, ORIGIN), false));
        stepFunctionBuilder.put(new ImmutableEntry<Boolean>(Position.of(PIXEL1, new Date(1L)), false));
        stepFunctionBuilder.putValueAt(true, Position.of(new Date(2L), PIXEL1));
        stepFunctionBuilder.putValueAt(true, Position.of(new Date(3L), PIXEL1));
        return stepFunctionBuilder.build();
    }

}
