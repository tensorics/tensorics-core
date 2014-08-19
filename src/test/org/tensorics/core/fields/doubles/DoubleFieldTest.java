/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.fields.doubles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.tensorics.core.math.structures.grouplike.AbelianGroup;

public class DoubleFieldTest {

    private static final double TOLERANCE = 0.00001;

    @Test
    public void additionIsNotNull() {
        assertNotNull(addition());
    }

    @Test
    public void multiplicationIsNotNull() {
        assertNotNull(multiplication());
    }

    @Test
    public void additionWorks() {
        assertEquals(2.5, addition().operation().perform(1.5, 1.0), TOLERANCE);
    }

    @Test
    public void multiplicationWorks() {
        assertEquals(5.0, multiplication().operation().perform(2.5, 2.0), TOLERANCE);
    }

    @Test
    public void zeroIsCorrect() {
        assertEquals(0.0, addition().identity(), TOLERANCE);
    }

    @Test
    public void oneIsCorrect() {
        assertEquals(1.0, multiplication().identity(), TOLERANCE);
    }

    @Test
    public void negativeWorks() {
        assertEquals(4.2, addition().inversion().perform(-4.2), TOLERANCE);
    }

    @Test
    public void inverseWorks() {
        assertEquals(0.5, multiplication().inversion().perform(2.0), TOLERANCE);
    }

    private AbelianGroup<Double> addition() {
        return field().additionStructure();
    }

    private AbelianGroup<Double> multiplication() {
        return field().multiplicationStructure();
    }

    private DoubleField field() {
        return new DoubleField();
    }

}
