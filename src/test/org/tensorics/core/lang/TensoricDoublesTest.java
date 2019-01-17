/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import static javax.measure.unit.SI.AMPERE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.measure.unit.Unit;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.EqualsWithDelta;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.units.JScienceUnit;

/**
 * Unit tests for {@link TensoricDoubles}
 * 
 * @author caguiler
 */
public class TensoricDoublesTest {

    private static final double DELTA = 10e-5;
    private List<QuantifiedValue<Double>> iterableSample;

    @Before
    public void setUp() {
        initIterableSample();
    }

    private void initIterableSample() {
        /**
         * <li>Total Numbers 10
         * <li>Sample: 1,2,3,4,5,6,7,8,9,10
         * <li>Mean (Average) 5.5
         * <li>RMS 6.204836823
         * <li>Standard deviation 2.87228
         * <li>Variance(Standard deviation) 8.25
         */
        iterableSample = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            QuantifiedValue<Double> value = TensoricDoubles.valueOf((double) i, JScienceUnit.of(AMPERE));
            iterableSample.add(value);
        }
    }

    @Test
    public void testRmsOfIterableOfQuantifiedDouble() {
        verifyQuantifiedIterableOperationResult(TensoricDoubles::rmsOfQ, 6.204836823, AMPERE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRmsOfEmptyIterableOfQuantifiedDouble() {
        iterableSample = Collections.emptyList();
        verifyQuantifiedIterableOperationResult(TensoricDoubles::rmsOfQ, 0.0, Unit.ONE);
    }

    @Test
    public void testStdOfIterableOfQuantifiedDouble() {
        verifyQuantifiedIterableOperationResult(TensoricDoubles::stdOfQ, 2.87228, AMPERE);
    }

    @Test
    public void testVarOfIterableOfQuantifiedDouble() {
        verifyQuantifiedIterableOperationResult(TensoricDoubles::varOfQ, 8.25, AMPERE.pow(2));
    }

    private void verifyQuantifiedIterableOperationResult(
            Function<Iterable<QuantifiedValue<Double>>, QuantifiedValue<Double>> operation, Double expectedResult,
            Unit<?> expectedUnit) {
        QuantifiedValue<Double> result = operation.apply(iterableSample);
        assertThat(result.unit(), is(JScienceUnit.of(expectedUnit)));
        assertThat(result.value(), is(Matchers.closeTo(expectedResult, DELTA)));
    }
}
