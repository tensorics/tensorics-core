package org.tensorics.core.resolve;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.DoubleScript;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tensor.ImmutableTensor;
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
                return ResolvedExpression.of(Tensorics.zeroDimensionalOf(0.4));
            }
        });
        assertEquals(0.4, result.get(), 0.00001);
    }

}
