/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity;

import static org.junit.Assert.assertEquals;

import javax.measure.unit.SI;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.units.JScienceUnit;

public class ImmutableQuantifiedScalarTest {

    private ImmutableQuantifiedValue<Double> scalar;

    @Before
    public void setUp() {
        scalar = Tensorics.quantityOf(10.5, JScienceUnit.of(SI.AMPERE));
    }

    @Test
    public void testUnit() {
        assertEquals(JScienceUnit.of(SI.AMPERE), scalar.unit());
    }

    @Test
    public void testValue() {
        assertEquals(10.5, scalar.value(), 0.000001);
    }

}
