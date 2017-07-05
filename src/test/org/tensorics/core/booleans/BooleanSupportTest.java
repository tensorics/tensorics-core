/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.tensorics.core.tensor.Tensor;

/**
 * Test to cover functionality of the service fluent API.
 * 
 * @author agorzaws
 */
public class BooleanSupportTest extends AbstractBooleanTest {

    @Test
    public void testBooleanScalarAlgebra() {
        Boolean with = calcLogical(true).and(false);
        assertFalse(with);
    }

    @Test
    public void testBooleanTensorAlgebraJustCalc() {
        Tensor<Boolean> tensorTrue = createTensorOf(true, 6, 4);
        Tensor<Boolean> tensorFalse = createTensorOf(false, 6, 4);
        Tensor<Boolean> tensorFalseOther = createTensorOf(false, 3, 2);
        calcLogical(tensorTrue).and(tensorTrue);

        BooleanSupport nonDefaultRegistrySupport = with(NON_DEFAULT_SHAPING);
        
        Tensor<Boolean> or = calcLogical(tensorTrue).or(tensorFalse);
        Tensor<Boolean> or2 = nonDefaultRegistrySupport.calcLogical(tensorTrue).or(tensorFalse);
        Tensor<Boolean> or3 = nonDefaultRegistrySupport.calcLogical(tensorTrue).or(tensorFalseOther);
        assertEquals(or.shape(), or2.shape());
        assertEquals(tensorFalseOther.shape(), or3.shape());
        
    }

    @Test
    public void testSetDifferentStrategy() {
        Tensor<Boolean> tensorFalse = createSimpleOneComparableDimensionTensorOf(false, 6, 1);
        Tensor<Boolean> tensorFalseOther = createSimpleOneComparableDimensionTensorOf(false, 6, 2);
        /* explicit shaping strategy to apply but */
        with(NON_DEFAULT_SHAPING).calcLogical(tensorFalseOther).and(tensorFalse);
    }
}
