/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import org.tensorics.core.math.operations.BinaryOperation;

/**
 * Repository for logical operations <lu><li>AND</li><li>NAND</li><li>OR</li><li>XOR</li></lu>
 * 
 * @author agorzaws
 */
public final class LogicalOperationsRepository {

    private LogicalOperationsRepository() {
        /* only static methods */
    }

    public static BinaryOperation<Boolean> and() {
        return (left, right) -> left && right;
    }

    public static BinaryOperation<Boolean> nand() {
        return (left, right) -> !(left && right);
    }

    public static BinaryOperation<Boolean> or() {
        return (left, right) -> left || right;
    }

    public static BinaryOperation<Boolean> xor() {
        return (left, right) -> left ^ right;
    }

}
