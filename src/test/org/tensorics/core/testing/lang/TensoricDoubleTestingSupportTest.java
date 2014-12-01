/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing.lang;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;

public class TensoricDoubleTestingSupportTest extends TensoricDoubleTestingSupport {

    @Test
    public void testScalarCloseToTrueMatcher() {
        assertThat(0.1, is(within(0.1).closeTo(0.0)));
    }

    @Test
    public void testScalarCloseToFalseMatcher() {
        assertThat(0.1, is(not(within(0.09).closeTo(0.0))));
    }

    @Test
    public void tensorCloseToMatcherMatchesMatchingTensor() {
        assertThat(zeroDotOne(), is(within(0.1).closeTo(zeroTensor())));
    }

    @Test
    public void tensorCloseToMatcherDoesNotMatchNonMatchingTensor() {
        assertThat(zeroDotOne(), is(not(within(0.09).closeTo(zeroTensor()))));
    }

    @Test
    public void tensorWithDifferentShapeDoesNotMatch() {
        assertThat(zeroDotOne(), is(not(within(0.1).closeTo(emptyDoubleTensor()))));
    }

    private Tensor<Double> emptyDoubleTensor() {
        return Tensorics.<Double> builder().build();
    }

    private Tensor<Double> zeroDotOne() {
        return Tensorics.zeroDimensionalOf(0.1);
    }

    private Tensor<Double> zeroTensor() {
        return Tensorics.zeroDimensionalOf(0.0);
    }

}
