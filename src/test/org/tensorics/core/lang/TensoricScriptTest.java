/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ExpressionIsUnresolvedException;

public class TensoricScriptTest {

    private TensoricScript<Double, Double> script;

    @Before
    public void setUpBeforeClass() throws Exception {
        script = new DoubleScript<Double>() {

            @Override
            protected Expression<Double> describe() {
                return null;
            }
        };
    }

    @Test
    public void scriptIsNotResolved() {
        assertFalse(script.isResolved());
    }

    @Test(expected = ExpressionIsUnresolvedException.class)
    public void scriptThrowsOnGet() {
        script.get();
    }
}
