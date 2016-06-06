/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarExpressionSupport;

public class FunctionExpressionSupport<V> extends ScalarExpressionSupport<V> {

    private final ExtendedField<V> field;

    public FunctionExpressionSupport(ExtendedField<V> field) {
        super(field);
        this.field = field;
    }
  
}
