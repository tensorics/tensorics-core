/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.stream.DoubleStream;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link MapBackedDiscreteFunction}
 * 
 * @author caguiler
 */
public class MapBackedDiscreteFunctionTest {

	private static final double UNDEFINED_X_VALUE = 33D;
	private static final int NUMBER_OF_DEFINED_ELEMENTS = 10;
	private MapBackedDiscreteFunction<Double, Double> identityFunction;

	@Before
	public void setUp() {
		MapBackedDiscreteFunction.Builder<Double, Double> builder = MapBackedDiscreteFunction.<Double, Double>builder();
		DoubleStream.iterate(1, i -> i + 1).limit(NUMBER_OF_DEFINED_ELEMENTS).forEach(i -> builder.put(i, i));
		identityFunction = builder.build();
	}

	@Test
	public void testApplyReturnsExpectedValuesForDefinedXs() {
		//@formatter:off
        DoubleStream.iterate(1, i -> i + 1)
                .limit(NUMBER_OF_DEFINED_ELEMENTS)
                .forEach(i -> assertTrue(identityFunction.apply(i).equals(i)));
        //@formatter:on
	}

	@Test(expected = IllegalDiscreteFunctionUsageException.class)
	public void testApplyThrowsExceptionWhenRequestedValueForUndefinedX() {
		identityFunction.apply(UNDEFINED_X_VALUE);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testImmutability() {
		Set<Double> definedXValues = identityFunction.definedXValues();
		definedXValues.add(UNDEFINED_X_VALUE);
	}

}
