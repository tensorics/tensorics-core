/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

/**
 * Factory methods for {@link LogicalOperation}s
 * 
 * @author agorzaws
 */
public final class LogicalOperationsFactory {

    private LogicalOperationsFactory() {
        /* only static methods */
    }

    public static LogicalANDOperation and() {
        return new LogicalANDOperation();
    }

    public static LogicalNANDOperation nand() {
        return new LogicalNANDOperation();
    }

    public static LogicalOROperation or() {
        return new LogicalOROperation();
    }

    public static LogicalXOROperation xor() {
        return new LogicalXOROperation();
    }
}
