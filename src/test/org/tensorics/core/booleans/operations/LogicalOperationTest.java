/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.tensorics.core.math.operations.BinaryFunction;


/**
 * A simple test for basics of the boolean operations.
 * 
 * @author agorzaws
 */
public class LogicalOperationTest {

    @ParameterizedTest
    @MethodSource("getParametersAnd")
    public void testAND(boolean input1, boolean input2, boolean result) {
        BinaryFunction<Boolean, Boolean> andOperation = BooleanOperations.and();
        Boolean resultOp = andOperation.perform(input1, input2);
        assertEquals(result, resultOp);
    }

    @ParameterizedTest
    @MethodSource("getParametersNand")
    public void testNAND(boolean input1, boolean input2, boolean result) {
        BinaryFunction<Boolean, Boolean> andOperation = BooleanOperations.nand();
        Boolean resultOp = andOperation.perform(input1, input2);
        assertEquals(result, resultOp);
    }

    @ParameterizedTest
    @MethodSource("getParametersXor")
    public void testXOR(boolean input1, boolean input2, boolean result) {
        BinaryFunction<Boolean, Boolean> andOperation = BooleanOperations.xor();
        Boolean resultOp = andOperation.perform(input1, input2);
        assertEquals(result, resultOp);
    }

    @ParameterizedTest
    @MethodSource("getParametersOr")
    public void testOR(boolean input1, boolean input2, boolean result) {
        BinaryFunction<Boolean, Boolean> andOperation = BooleanOperations.or();
        Boolean resultOp = andOperation.perform(input1, input2);
        assertEquals(result, resultOp);
    }

    static Object[] getParametersAnd() {
        Object[] params = new Object[4];
        params[0] = new Object[] { true, true, true };
        params[1] = new Object[] { true, false, false };
        params[2] = new Object[] { false, true, false };
        params[3] = new Object[] { false, false, false };

        return params;
    }

    static Object[] getParametersNand() {
        Object[] params = new Object[4];
        params[0] = new Object[] { true, true, false };
        params[1] = new Object[] { true, false, true };
        params[2] = new Object[] { false, true, true };
        params[3] = new Object[] { false, false, true };

        return params;
    }

    static Object[] getParametersXor() {
        Object[] params = new Object[4];
        params[0] = new Object[] { true, true, false };
        params[1] = new Object[] { true, false, true };
        params[2] = new Object[] { false, true, true };
        params[3] = new Object[] { false, false, false };
        return params;
    }

    static Object[] getParametersOr() {
        Object[] params = new Object[4];
        params[0] = new Object[] { true, true, true };
        params[1] = new Object[] { true, false, true };
        params[2] = new Object[] { false, true, true };
        params[3] = new Object[] { false, false, false };
        return params;
    }

}
