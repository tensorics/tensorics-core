// @formatter:off
/*******************************************************************************
 * This file is part of tensorics.
 * <p>
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.resolve;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.DoubleScript;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * The aim of this test is to ensure the correct resolving of binary predicates
 *
 * @author caguiler
 */
public class BinaryPredicateExpressionTest {

    private ResolvingEngine engine;

    @Before
    public void setUp() {
        engine = ResolvingEngines.defaultEngine();
    }

    @Test
    public void testIsLessThanForScalarExpressions() throws Exception {
        Boolean trueResult = engine.resolve(new DoubleScript<Boolean>() {
            @Override
            protected Expression<Boolean> describe() {
                Expression<Double> sum = calculate(1.0).plus(0.5);
                return testIf(sum).isLessThan(2.0);
            }
        });

        assertTrue(trueResult);

        Boolean falseResult = engine.resolve(new DoubleScript<Boolean>() {
            @Override
            protected Expression<Boolean> describe() {
                Expression<Double> sum = calculate(1.0).plus(2.5);
                return testIf(sum).isLessThan(2.0);
            }
        });

        assertFalse(falseResult);
    }
    @Test
    public void testIsLessThanForIterableExpressions() throws Exception {

        Expression<Iterable<Double>> iterableExpression = ResolvedExpression.of(Arrays.asList(1D, 2D, 3D, 4D, 5D));

        Boolean trueResult = engine.resolve(new DoubleScript<Boolean>() {
            @Override
            protected Expression<Boolean> describe() {
                return testIfIt(iterableExpression).isLessThan(10D);
            }
        });

        assertTrue(trueResult);

        Boolean falseResult = engine.resolve(new DoubleScript<Boolean>() {
            @Override
            protected Expression<Boolean> describe() {
                return testIfIt(iterableExpression).isLessThan(2D);
            }
        });

        assertFalse(falseResult);
    }
}
