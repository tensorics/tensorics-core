/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResolvedExpressionTest {

    @Test
    public void getReturnsCorrectValue() {
        assertEquals(Integer.valueOf(1), ResolvedExpression.of(1).get());
    }

}
