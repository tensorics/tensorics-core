/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
