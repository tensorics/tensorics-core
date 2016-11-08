/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

public class LogicalXOROperation extends AbstractLogicalOperation implements LogicalOperation {

    @Override
    public Boolean getResult(Boolean left, Boolean right) {
        return left ^ right;
    }
}
