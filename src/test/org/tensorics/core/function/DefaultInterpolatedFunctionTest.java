/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.function.interpolation.InterpolationStrategy;

import com.google.common.collect.ImmutableSet;

/**
 * Ensures that the contract of {@link DefaultInterpolatedFunction} is
 * fulfilled.
 * 
 * @author caguiler
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultInterpolatedFunctionTest {

	private static final double DEFINED_X_VALUE = 1.0;
	private static final double UNDEFINED_X_VALUE = 33.0;
	private static final Comparator<Double> COMPARATOR = Double::compareTo;

	@Mock
	DiscreteFunction<Double, Double> function;
	@Mock
	InterpolationStrategy<Double> interpolationStrategy;
	@Mock
	Conversion<Double, Double> conversion;

	DefaultInterpolatedFunction<Double, Double> interpolatedFunction;

	@Before
	public void setUp() {
		when(function.definedXValues()).thenReturn(ImmutableSet.of(DEFINED_X_VALUE));
		when(function.apply(DEFINED_X_VALUE)).thenReturn(DEFINED_X_VALUE);
		interpolatedFunction = new DefaultInterpolatedFunction<>(function, interpolationStrategy, conversion,
				COMPARATOR);
	}

	@Test
	public void testDefinedXValuesAreTheSameThanBackedDiscreteFunction() {
		assertEquals(interpolatedFunction.definedXValues(), function.definedXValues());
	}

	@Test
	public void testItDoesNotRelyOnInterpolationStrategyForDefinedXValues() {
		interpolatedFunction.apply(DEFINED_X_VALUE);
		verify(function, times(1)).apply(DEFINED_X_VALUE);
		verify(interpolationStrategy, never()).interpolate(any(), any(), any(), any());
	}

	@Test
	public void testItReliesOnInterpolationStrategyForUnDefinedXValues() {
		interpolatedFunction.apply(UNDEFINED_X_VALUE);
		verify(interpolationStrategy, times(1)).interpolate(UNDEFINED_X_VALUE, function, conversion, COMPARATOR);
	}

}
