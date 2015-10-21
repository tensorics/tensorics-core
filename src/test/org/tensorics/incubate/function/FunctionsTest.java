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

package org.tensorics.incubate.function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tensorics.incubate.function.SortedMapBackedDiscreteFunction.Builder;

public class FunctionsTest {

    private DiscreteFunction<Double, Double> function;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // *
    }

    @Test
    public void testLinearFunctionZeroToFive() {
        function = createLinearFunction();
        double integrateResult = Functions.integrate(function);
        Assert.assertEquals(12.5, integrateResult, 0.01);
    }

    @Test
    public void testSquereFunctionOneToThree() {
        function = createSquereFunction();
        double integrateResult = Functions.integrate(function, 1.0, 3.0);
        Assert.assertEquals(8.666, integrateResult, 0.01);
    }

    private DiscreteFunction<Double, Double> createSquereFunction() {
        Builder<Double, Double> functionBuilder = SortedMapBackedDiscreteFunction.builder();
        for (int i = 0; i < 5001; i++) {
            double x = i * 0.001;
            functionBuilder.put(x, x * x);
        }
        return functionBuilder.build();
    }

    private DiscreteFunction<Double, Double> createLinearFunction() {
        Builder<Double, Double> functionBuilder = SortedMapBackedDiscreteFunction.builder();
        for (int i = 0; i < 5001; i++) {
            double x = i * 0.001;
            functionBuilder.put(x, x);
        }
        return functionBuilder.build();
    }

}
