/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

/**
 * Tests the behaviour of {@link FunctionExpressionSupport}
 * 
 * @author caguiler
 */
public class FunctionExpressionSupportTest {

	private ResolvingEngine engine = ResolvingEngines.defaultEngine();
	private Expression<DiscreteFunction<Double, Double>> two;
	private Expression<DiscreteFunction<Double, Double>> three;
	private Expression<DiscreteFunction<Double, Double>> complex;

	private FunctionExpressionSupport<Double> support;

	@Before
	public void setUp() {

		engine = ResolvingEngines.defaultEngine();
		MapBackedDiscreteFunction.Builder<Double, Double> builder2 = MapBackedDiscreteFunction.builder();
		MapBackedDiscreteFunction.Builder<Double, Double> builder3 = MapBackedDiscreteFunction.builder();
		MapBackedDiscreteFunction.Builder<Double, Double> builder4 = MapBackedDiscreteFunction.builder();

		for (double i = 1; i < 5; i += 2) {

			builder2.put(i, 2D);
			builder3.put(i + 1, 3D);
		}

		two = ResolvedExpression.of(builder2.build());
		three = ResolvedExpression.of(builder3.build());

		//@formatter:off
        /*
         * Info about the function called complex:
         * - avg = 5
         * - variance = 4
         * - std = 2
         * 
         */
        //@formatter:on
		double yValues[] = { 2, 4, 4, 4, 5, 5, 7, 9 };
		for (int i = 0; i < 8; ++i) {
			builder4.put((double) i, yValues[i]);
		}
		complex = ResolvedExpression.of(builder4.build());

		support = new FunctionExpressionSupport<Double>(
				EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));

	}

	@Test
	public void testPlus() {
		Expression<DiscreteFunction<Double, Double>> unResolved5 = support.calculateF(two).plus(three);
		DiscreteFunction<Double, Double> five = engine.resolve(unResolved5);
		assertEquals(Sets.union(two.get().definedXValues(), three.get().definedXValues()), five.definedXValues());
		assertTrue(allYValuesEqualTo(five, 5.0));
	}

	@Test
	public void testMinus() {
		Expression<DiscreteFunction<Double, Double>> unResolvedMinusOne = support.calculateF(two).minus(three);
		DiscreteFunction<Double, Double> minusOne = engine.resolve(unResolvedMinusOne);

		assertEquals(Sets.union(two.get().definedXValues(), three.get().definedXValues()), minusOne.definedXValues());
		assertTrue(allYValuesEqualTo(minusOne, -1.0));

		Expression<DiscreteFunction<Double, Double>> unResolvedOne = support.calculateF(three).minus(two);
		DiscreteFunction<Double, Double> one = engine.resolve(unResolvedOne);

		assertEquals(Sets.union(two.get().definedXValues(), three.get().definedXValues()), one.definedXValues());
		assertTrue(allYValuesEqualTo(one, 1.0));
	}

	@Test
	public void testTimes() {
		Expression<DiscreteFunction<Double, Double>> unResolvedSix = support.calculateF(two).times(three);
		DiscreteFunction<Double, Double> six = engine.resolve(unResolvedSix);

		assertEquals(Sets.union(two.get().definedXValues(), three.get().definedXValues()), six.definedXValues());
		assertTrue(allYValuesEqualTo(six, 6.0));
	}

	@Test
	public void testDividedBy() {
		Expression<DiscreteFunction<Double, Double>> unResolvedTwoThirds = support.calculateF(two).dividedBy(three);
		DiscreteFunction<Double, Double> twoThirds = engine.resolve(unResolvedTwoThirds);

		assertEquals(Sets.union(two.get().definedXValues(), three.get().definedXValues()), twoThirds.definedXValues());
		assertTrue(allYValuesEqualTo(twoThirds, 2 / 3.));

		Expression<DiscreteFunction<Double, Double>> unResolvedThreeHalfs = support.calculateF(three).dividedBy(two);
		DiscreteFunction<Double, Double> threeHalfs = engine.resolve(unResolvedThreeHalfs);

		assertEquals(Sets.union(two.get().definedXValues(), three.get().definedXValues()), threeHalfs.definedXValues());
		assertTrue(allYValuesEqualTo(threeHalfs, 3 / 2.));
	}

	@Test
	public void testRmsOfF() {
		Expression<Double> unresolvedTwo = support.rmsOfF(two);
		Double dos = engine.resolve(unresolvedTwo);
		assertEquals(2, dos, Double.MIN_VALUE);
	}

	@Test
	public void testAvgOfF() {
		Expression<Double> unresolvedTwo = support.averageOfF(two);
		Double dos = engine.resolve(unresolvedTwo);
		assertEquals(2, dos, Double.MIN_VALUE);
	}

	@Test
	public void testStdOfF() {
		Expression<Double> unresolvedTwo = support.stdOfF(complex);
		Double dos = engine.resolve(unresolvedTwo);
		assertEquals(2, dos, Double.MIN_VALUE);

		Expression<Double> unresolvedZero = support.stdOfF(two);
		Double cero = engine.resolve(unresolvedZero);
		assertEquals(0, cero, Double.MIN_VALUE);
	}

	private <X, Y> boolean allYValuesEqualTo(DiscreteFunction<X, Y> function, Y value) {
		return function.definedXValues().stream().map(function::apply).allMatch(y -> y.equals(value));
	}
}
