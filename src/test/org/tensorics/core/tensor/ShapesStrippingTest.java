/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class ShapesStrippingTest {

    @Test
    public void test() {
        Shape shape = new Shape(ImmutableSet.of(Dim1.class), ImmutableSet.of(Position.of(Dim1Enum.P1)));
        Shape stripped = Shapes.dimensionStripped(shape, ImmutableSet.of(Dim1.class));
        Assertions.assertThat(stripped.dimensionality()).isEqualTo(0);
    }
    
    
    private interface Dim1 {
        /* Only for testing */
    }
    
    private enum Dim1Enum implements Dim1 {
        P1, P2;
    }

}
