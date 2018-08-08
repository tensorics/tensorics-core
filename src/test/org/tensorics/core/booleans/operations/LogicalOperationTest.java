/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.tensorics.core.math.operations.BinaryFunction;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * A simple test for basics of the boolean operations.
 * 
 * @author agorzaws
 */
@RunWith(JUnitParamsRunner.class)
public class LogicalOperationTest {

    @Test
    @Parameters(method = "getParametersAnd")
    public void testAND(boolean input1, boolean input2, boolean result) {
        BinaryFunction<Boolean, Boolean> andOperation = BooleanOperations.and();
        Boolean resultOp = andOperation.perform(input1, input2);
        assertEquals(result, resultOp);
    }

    @Test
    @Parameters(method = "getParametersNand")
    public void testNAND(boolean input1, boolean input2, boolean result) {
        BinaryFunction<Boolean, Boolean> andOperation = BooleanOperations.nand();
        Boolean resultOp = andOperation.perform(input1, input2);
        assertEquals(result, resultOp);
    }

    @Test
    @Parameters(method = "getParametersXor")
    public void testXOR(boolean input1, boolean input2, boolean result) {
        BinaryFunction<Boolean, Boolean> andOperation = BooleanOperations.xor();
        Boolean resultOp = andOperation.perform(input1, input2);
        assertEquals(result, resultOp);
    }

    @Test
    @Parameters(method = "getParametersOr")
    public void testOR(boolean input1, boolean input2, boolean result) {
        BinaryFunction<Boolean, Boolean> andOperation = BooleanOperations.or();
        Boolean resultOp = andOperation.perform(input1, input2);
        assertEquals(result, resultOp);
    }

    public Object[] getParametersAnd() {
        Object[] params = new Object[4];
        params[0] = new Object[] { true, true, true };
        params[1] = new Object[] { true, false, false };
        params[2] = new Object[] { false, true, false };
        params[3] = new Object[] { false, false, false };

        return params;
    }

    public Object[] getParametersNand() {
        Object[] params = new Object[4];
        params[0] = new Object[] { true, true, false };
        params[1] = new Object[] { true, false, true };
        params[2] = new Object[] { false, true, true };
        params[3] = new Object[] { false, false, true };

        return params;
    }

    public Object[] getParametersXor() {
        Object[] params = new Object[4];
        params[0] = new Object[] { true, true, false };
        params[1] = new Object[] { true, false, true };
        params[2] = new Object[] { false, true, true };
        params[3] = new Object[] { false, false, false };
        return params;
    }

    public Object[] getParametersOr() {
        Object[] params = new Object[4];
        params[0] = new Object[] { true, true, true };
        params[1] = new Object[] { true, false, true };
        params[2] = new Object[] { false, true, true };
        params[3] = new Object[] { false, false, false };
        return params;
    }

}
