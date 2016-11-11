/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import org.tensorics.core.math.operations.BinaryOperation;

/**
 * Repository for logical operations <lu>
 * <li>AND</li>
 * <li>NAND</li>
 * <li>OR</li>
 * <li>XOR</li></lu>
 * 
 * @author agorzaws
 */
public final class BooleanOperations {

    private static final BinaryOperation<Boolean> AND = (left, right) -> left && right;
    private static final BinaryOperation<Boolean> NAND = (left, right) -> !(left && right);
    private static final BinaryOperation<Boolean> OR = (left, right) -> left || right;
    private static final BinaryOperation<Boolean> XOR = (left, right) -> left ^ right;

    private BooleanOperations() {
        /* only static methods */
    }

    public static BinaryOperation<Boolean> and() {
        return AND;
    }

    public static BinaryOperation<Boolean> nand() {
        return NAND;
    }

    public static BinaryOperation<Boolean> or() {
        return OR;
    }

    public static BinaryOperation<Boolean> xor() {
        return XOR;
    }

}
