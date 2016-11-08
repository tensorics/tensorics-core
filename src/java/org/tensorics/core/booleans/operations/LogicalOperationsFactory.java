/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import org.tensorics.core.math.operations.BinaryOperation;

/**
 * Factory methods for {@link LogicalOperation}s
 * 
 * @author agorzaws
 */
public final class LogicalOperationsFactory {

    private LogicalOperationsFactory() {
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
