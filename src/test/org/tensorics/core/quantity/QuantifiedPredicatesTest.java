/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.tensorics.core.lang.DoubleTensorics.confidenceLevelOf;

import javax.measure.unit.SI;

import org.junit.Test;
import org.tensorics.core.lang.DoubleTensorics;
import org.tensorics.core.lang.TensoricSupport;
import org.tensorics.core.lang.Tensorics;

public class QuantifiedPredicatesTest {

    @Test
    public void compareAtDifferentConfidenceLevels() {
        QuantifiedValue<Double> v1 = Tensorics.quantityOf(90.0, SI.METER).withError(1.0);
        QuantifiedValue<Double> v2 = Tensorics.quantityOf(100.0, SI.METER).withError(5.0);
        QuantifiedValue<Double> v3 = Tensorics.quantityOf(100.0, SI.METER).withError(10.0);

        assertTrue(DoubleTensorics.testIf(v1).isLessThan(v2));
        assertFalse(DoubleTensorics.testIf(v1).isLessThan(v3));

        final TensoricSupport<Double> withLowConfidence = DoubleTensorics.with(confidenceLevelOf(0.68));
        assertTrue(withLowConfidence.testIf(v1).isLessThan(v2));
        assertTrue(withLowConfidence.testIf(v1).isLessThan(v3));
    }    

    @Test
    public void compareExactToErrorous() {
        ImmutableQuantifiedValue<Double> v1 = Tensorics.quantityOf(99.9, SI.METER);
        ImmutableQuantifiedValue<Double> v2 = Tensorics.quantityOf(100.0, SI.METER).withError(0.1);
        
        assertFalse(DoubleTensorics.testIf(v1).isLessThan(v2));
        assertFalse(DoubleTensorics.testIf(v2).isGreaterThan(v1));
        assertFalse(DoubleTensorics.testIf(v2).isLessThan(v1));
        assertFalse(DoubleTensorics.testIf(v1).isGreaterThan(v2));

        v1 = v1.withError(0.0);
        v2 = v2.withError(0.01);
        
        assertTrue(DoubleTensorics.testIf(v1).isLessThan(v2));
        assertTrue(DoubleTensorics.testIf(v2).isGreaterThan(v1));
        assertFalse(DoubleTensorics.testIf(v2).isLessThan(v1));
        assertFalse(DoubleTensorics.testIf(v1).isGreaterThan(v2));
    }
    
    @Test
    public void compareExactValues() {
        ImmutableQuantifiedValue<Double> v1 = Tensorics.quantityOf(99.9, SI.METER);
        ImmutableQuantifiedValue<Double> v2 = Tensorics.quantityOf(100.0, SI.METER);
        
        assertTrue(DoubleTensorics.testIf(v1).isLessThan(v2));
        assertTrue(DoubleTensorics.testIf(v2).isGreaterThan(v1));
        assertFalse(DoubleTensorics.testIf(v2).isLessThan(v1));
        assertFalse(DoubleTensorics.testIf(v1).isGreaterThan(v2));

        v1 = v1.withError(0.0);
        v2 = v2.withError(0.0);
        
        assertTrue(DoubleTensorics.testIf(v1).isLessThan(v2));
        assertTrue(DoubleTensorics.testIf(v2).isGreaterThan(v1));
        assertFalse(DoubleTensorics.testIf(v2).isLessThan(v1));
        assertFalse(DoubleTensorics.testIf(v1).isGreaterThan(v2));
    }

    @Test
    public void compareZeros() {
        QuantifiedValue<Double> v1 = Tensorics.quantityOf(0.0, SI.METER).withError(0.0);
        QuantifiedValue<Double> v2 = Tensorics.quantityOf(0.0, SI.METER).withError(0.0);
        
        assertFalse(DoubleTensorics.testIf(v1).isLessThan(v2));
        assertFalse(DoubleTensorics.testIf(v1).isGreaterThan(v2));
    }

    @Test
    public void compareEqualValue() {
        QuantifiedValue<Double> v1 = Tensorics.quantityOf(99.9, SI.METER);
        
        assertFalse(DoubleTensorics.testIf(v1).isLessThan(v1));
        assertFalse(DoubleTensorics.testIf(v1).isGreaterThan(v1));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void throwOnInconsistentUnits() {
        QuantifiedValue<Double> v1 = Tensorics.quantityOf(100.0, SI.METER).withError(90.0);
        QuantifiedValue<Double> v2 = Tensorics.quantityOf(190.0, SI.AMPERE).withError(1.0);
        
        DoubleTensorics.testIf(v1).isLessThan(v2);
        DoubleTensorics.testIf(v1).isGreaterThan(v2);
    }
    @Test
    public void convertUnitsForComparison() {
        QuantifiedValue<Double> v1 = Tensorics.quantityOf(1.0, SI.METER).withError(0.01);
        QuantifiedValue<Double> v2 = Tensorics.quantityOf(900.0, SI.MILLIMETER).withError(1.0);

        assertFalse(DoubleTensorics.testIf(v1).isLessThan(v2));
        assertTrue(DoubleTensorics.testIf(v1).isGreaterThan(v2));
    }    

}
