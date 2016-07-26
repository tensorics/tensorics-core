/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.TensoricExpressions;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class LambdaExpressionsTest {

    private ResolvingEngine engine;

    @Before
    public void setUp() {
        engine = ResolvingEngines.defaultEngine();
    }

    @Test
    public void ringOneServedResultsInOkUsingExpression() throws Exception {
        Expression<Boolean> ring1ServedExpression = ResolvedExpression.of(true);
        Expression<Boolean> ring2ServedExpression = ResolvedExpression.of(false);
        Expression<Ring> requestedRingExpression = ResolvedExpression.of(Ring.RING1);

        Expression<Status> conditionExpression = TensoricExpressions
                .use(requestedRingExpression, ring1ServedExpression, ring2ServedExpression).in(this::evaluateCondition);

        assertEquals(Status.OK, engine.resolve(conditionExpression));
    }

    @Test
    public void noRingServed() throws Exception {
        Expression<Boolean> ring1ServedExpression = ResolvedExpression.of(false);
        Expression<Boolean> ring2ServedExpression = ResolvedExpression.of(false);
        Expression<Ring> requestedRing1 = ResolvedExpression.of(Ring.RING1);
        Expression<Ring> requestedRing2 = ResolvedExpression.of(Ring.RING2);

        Expression<Status> withRing1 = TensoricExpressions
                .use(requestedRing1, ring1ServedExpression, ring2ServedExpression).in(this::evaluateCondition);
        Expression<Status> withRing2 = TensoricExpressions
                .use(requestedRing2, ring1ServedExpression, ring2ServedExpression).in(this::evaluateCondition);

        assertEquals(Status.NOT_OK, engine.resolve(withRing1));
        assertEquals(Status.NOT_OK, engine.resolve(withRing2));
    }

    @Test
    public void ringOneServedResultsInOkInPlainJava() {
        assertEquals(Status.OK, evaluateCondition(Ring.RING1, true, false));
    }

    private Status evaluateCondition(Ring requestedRing, boolean ring1Served, boolean ring2Served) {
        if (Ring.RING1.equals(requestedRing) && ring1Served) {
            return Status.OK;
        } else if (Ring.RING2.equals(requestedRing) && ring2Served) {
            return Status.OK;
        }
        return Status.NOT_OK;
    }

    private static enum Ring {
        RING1,
        RING2;
    }

    private static enum Status {
        OK,
        NOT_OK;
    }

}
