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
package org.tensorics.core.resolve;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.DoubleScript;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class DeferredTensoricCalculationTest {

    private static final String A_STRING = "A String";
    private ResolvingEngine engine;

    @Before
    public void setUp() {
        engine = ResolvingEngines.defaultEngine();
    }

    @Test
    public void testResolveResolvedExpression() {
        assertEquals(A_STRING, engine.resolve(ResolvedExpression.of(A_STRING)));
    }

    @Test
    public void testSimpleResolvedExpressionScript() {
        Double result = engine.resolve(new DoubleScript<Double>() {
            @Override
            protected Expression<Double> describe() {
                return ResolvedExpression.of(0.15);
            }
        });
        assertEquals(0.15, result, 0.00001);
    }

    @Test
    public void testMoreComplicatedScript() throws Exception {
        Double result = engine.resolve(new DoubleScript<Double>() {
            @Override
            protected Expression<Double> describe() {
                Expression<Double> sum = calculate(0.1).plus(0.2);
                return calculate(sum).toThePowerOf(2.0);
            }
        });
        assertEquals(0.09, result, 0.00001);
    }

    @Test
    public void testTensorScript() throws Exception {
        Tensor<Double> result = engine.resolve(new DoubleScript<Tensor<Double>>() {

            @Override
            protected Expression<Tensor<Double>> describe() {
                return ResolvedExpression.of(Tensorics.scalarOf(0.4));
            }
        });
        assertEquals(0.4, result.get(), 0.00001);
    }

}
