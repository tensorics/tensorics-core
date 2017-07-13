/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.tensorics.core.expressions.LatestOfExpression.latestOf;
import static org.tensorics.core.tree.domain.ResolvedExpression.of;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class LatestOfExpressionTest {

    private ResolvingEngine engine;

    @Before
    public void setUp() {
        engine = ResolvingEngines.defaultEngine();
    }

    @Test
    public void testLatestValue() {
        Expression<Iterable<Integer>> buffer = ResolvedExpression.of(Arrays.asList(1, 2, 3, 4));
        Expression<Integer> latestFromBuffer = latestOf(buffer);

        assertThat(engine.resolve(latestFromBuffer)).isEqualTo(4);
    }

    @Test
    public void equality() {
        assertThat(latestOf(of(asList(new Integer(300))))).isEqualTo(latestOf(of(asList(300))));
    }

}
