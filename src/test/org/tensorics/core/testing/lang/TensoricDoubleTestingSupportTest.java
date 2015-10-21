// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

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
