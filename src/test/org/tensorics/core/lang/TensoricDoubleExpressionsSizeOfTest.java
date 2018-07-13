package org.tensorics.core.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.tensorics.core.lang.TensoricDoubleExpressions.averageOf;
import static org.tensorics.core.lang.TensoricDoubleExpressions.rmsOf;
import static org.tensorics.core.lang.TensoricDoubleExpressions.sizeOf;
import static org.tensorics.core.lang.TensoricDoubleExpressions.sumOfSquaresOf;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

import com.google.common.collect.ImmutableList;

/**
 * The main aim of these tests is to see that they compile. They show different use-cases of the sizeOf expressions and
 * check if they are correctly operating.
 * 
 * @author kaifox
 */
public class TensoricDoubleExpressionsSizeOfTest {

    private ResolvingEngine engine = ResolvingEngines.defaultEngine();
    private ResolvedExpression<List<Double>> doubleExpr;

    @Before
    public void setUp() {
        doubleExpr = ResolvedExpression.of(ImmutableList.of(1.0, 2.0));
    }

    @Test
    public void sizeOfWorksWithListOfDoubles() {
        Double size = engine.resolve(sizeOf(doubleExpr));
        assertThat(size).isCloseTo(2.0, offset(0.001));
    }

    @Test
    public void sizeOfWorksWithListOfAnyType() {
        ResolvedExpression<List<String>> exp = ResolvedExpression.of(ImmutableList.of("A", "B"));
        Expression<Double> size = TensoricDoubleExpressions.sizeOf(exp);
        Double sizeVal = engine.resolve(size);
        assertThat(sizeVal).isCloseTo(2.0, offset(0.001));
    }

    @Test
    public void avarageWorksWithExpOfList() {
        Double size = engine.resolve(averageOf(doubleExpr));
        assertThat(size).isCloseTo(1.5, offset(0.001));
    }

    @Test
    public void rmsWorksWithExpOfList() {
        Double size = engine.resolve(rmsOf(doubleExpr));
        assertThat(size).isCloseTo(1.581, offset(0.001));
    }

    @Test
    public void sumOfSquaresWorksWithExpOfList() {
        Double size = engine.resolve(sumOfSquaresOf(doubleExpr));
        assertThat(size).isCloseTo(5.0, offset(0.001));
    }

    @Test
    public void sumWorksWithExpOfList() {
        Double size = engine.resolve(TensoricDoubleExpressions.sumOf(doubleExpr));
        assertThat(size).isCloseTo(3.0, offset(0.001));
    }

}
