/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tensorics.incubate.function.SortedMapBackedDiscreteFunction.Builder;

public class DiscreteFunctionTest {

    private DiscreteFunction<Double, Double> functionToTest;

    private static final List<Double> VALUES = new ArrayList<>();
    static {
        VALUES.add(5.0);
        VALUES.add(4.9);
        VALUES.add(4.8);
        VALUES.add(4.7);
        VALUES.add(4.6);
        VALUES.add(3.8);
        VALUES.add(3.5);
        VALUES.add(3.2);
        VALUES.add(-3.2);
        VALUES.add(-2.0);
        VALUES.add(-6.0);
    }

    @Test
    public void testGetYForXMapSorted() {
        Builder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();
        createDicreteFunction(builder);
        functionToTest = builder.build();
        assertEquals(3.2, functionToTest.getY(7.), 0.0);
        assertEquals(VALUES, functionToTest.getYs());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetYforNonExistinXForMapSorted() {
        Builder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();
        createDicreteFunction(builder);
        functionToTest = builder.build();
        assertEquals(3.2, functionToTest.getY(1122.), 0.0);
    }

    @Test
    public void testGetYInterpolated() {
        Builder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();
        createDicreteFunction(builder);
        functionToTest = builder.build();
        assertEquals(4.75, functionToTest.getY(2.5), 0.001);
        assertEquals(-4.00, functionToTest.getY(9.5), 0.001);
        assertEquals(0.00, functionToTest.getY(7.5), 0.001);
    }

    /**
     * @param builder
     */
    private void createDicreteFunction(DiscreteFunctionBuilder<Double, Double> builder) {
        for (int i = 0; i < VALUES.size(); i++) {
            builder.put(i * 1., VALUES.get(i));
        }
        builder.withInterpolationStrategy(new LinearInterpolationStrategy());
    }

}
