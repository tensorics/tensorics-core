/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.ImmutableEntry;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensor.lang.TensorStructurals;

public class BooleanStepFunctionExample {

    private static final Signal PIXEL1 = Signal.of("pixel1");
    private static final Signal AMPLIFIER = Signal.of("amplifier");
    private static final Date ORIGIN = new Date(0L);
    private Tensor<Boolean> stepFunctionTensor;

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
        Tensor<Boolean> tensor = stepFunctionTensor;
        Tensor<Boolean> slicedTensor = TensorStructurals.from(tensor).reduce(Signal.class).bySlicingAt(AMPLIFIER);
        assertFalse(slicedTensor.get(ORIGIN));
        assertEquals(Position.of(AMPLIFIER), slicedTensor.context().getPosition());

        TensorBuilder<Boolean> builder = Tensorics.builderFrom(tensor);
        builder.putAt(false,Position.of(new Date(5L), AMPLIFIER));
        builder.putAt(true,Position.of(new Date(6L), AMPLIFIER));

        assertEquals(true, builder.build().get(Position.of(AMPLIFIER, new Date(6L))));
        assertEquals(false, builder.build().get(Position.of(ORIGIN, AMPLIFIER)));

        Tensor<Boolean> secondTensor = builder.build();
        
        TensorStructurals.from(stepFunctionTensor);
    }

    private Tensor<Boolean> createStepFunction() {
        TensorBuilder<Boolean> stepFunctionBuilder = Tensorics.builder(Date.class, Signal.class);
        stepFunctionBuilder.putAt(false, ORIGIN, AMPLIFIER);
        stepFunctionBuilder.put(new ImmutableEntry<Boolean>(Position.of(PIXEL1, ORIGIN), false));
        stepFunctionBuilder.put(new ImmutableEntry<Boolean>(Position.of(PIXEL1, new Date(1L)), false));
        stepFunctionBuilder.putAt(true, Position.of(new Date(2L), PIXEL1));
        stepFunctionBuilder.putAt(true, Position.of(new Date(3L), PIXEL1));
        return stepFunctionBuilder.build();
    }

}
