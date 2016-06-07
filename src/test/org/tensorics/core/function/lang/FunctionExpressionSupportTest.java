/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.MapBackedDiscreteFunction;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.ManipulationOptions;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

import com.google.common.collect.Sets;

public class FunctionExpressionSupportTest {

    private ResolvingEngine engine = ResolvingEngines.defaultEngine();
    private Expression<DiscreteFunction<Double, Double>> two;
    private Expression<DiscreteFunction<Double, Double>> three;

    private FunctionExpressionSupport<Double> support;

    @Before
    public void setUp() {

        engine = ResolvingEngines.defaultEngine();
        MapBackedDiscreteFunction.Builder<Double, Double> builder2 = MapBackedDiscreteFunction.builder();
        MapBackedDiscreteFunction.Builder<Double, Double> builder3 = MapBackedDiscreteFunction.builder();

        for (double i = 1; i < 5; i += 2) {

            builder2.put(i, 2D);
            builder3.put(i + 1, 3D);
        }

        two = ResolvedExpression.of(builder2.build());
        three = ResolvedExpression.of(builder3.build());

        support = new FunctionExpressionSupport<Double>(
                EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));

    }

    @Test
    public void testPlus() {
        Expression<DiscreteFunction<Double, Double>> plus = support.calculateF(two).plus(three);

        DiscreteFunction<Double, Double> five = engine.resolve(plus);

        assertEquals(Sets.union(two.get().definedXValues(), three.get().definedXValues()), five.definedXValues());
    }

}
