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

import static org.tensorics.core.fields.doubles.Structures.doubles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.iterable.lang.ScalarIterableExpressionSupport;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.collect.ImmutableList;

public class ScalarIterableExpressionSupportTest {

    private ScalarIterableExpressionSupport<Double> usage;
    private ResolvingEngine resolvingEngine;

    @Before
    public void setUp() {
        usage = new ScalarIterableExpressionSupport<>(doubles());
        resolvingEngine = ResolvingEngines.defaultEngine();
    }

    @Test
    public void testAverageAndAddition() {
        Expression<Double> average = usage.averageOf(ImmutableList.of(1.0, 2.0, 3.0, 4.0));
        Expression<Double> sum = usage.calculate(average).plus(2.4);
        Assert.assertEquals(4.9, resolvingEngine.resolve(sum), 0.0001);
        System.out.println(sum);
    }

}
