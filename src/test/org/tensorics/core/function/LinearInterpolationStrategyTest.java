package org.tensorics.core.function;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.fields.doubles.Structures;

import com.google.common.base.Function;

public class LinearInterpolationStrategyTest {

	private static final double ERROR_TOLERANCE = 0.0001;

	public static final Function<Integer, Double> INTEGER_TO_DOUBLE = new Function<Integer, Double>() {
		@Override
		public Double apply(Integer t) {
			return t.doubleValue();
		}
	};

	private LinearInterpolationStrategy<Integer, Double> strategy;

	@Before
	public void setUp() {
		strategy = new LinearInterpolationStrategy<Integer, Double>(Structures.doubles(), INTEGER_TO_DOUBLE);
	}

	@Test
	public void testSimpleInterpolationWithValuesWithinDefinedXValuesInterval() {
		int from = 1;
		int to = 20;
		DiscreteFunction<Integer, Double> xAxisFrom1to20 = xAxis(from, to);

		for (int i = from + 1; i < to; ++i) {
			Double interpolated = strategy.interpolate(i, xAxisFrom1to20);
			Double expected = INTEGER_TO_DOUBLE.apply(i);
			assertEquals(expected, interpolated, ERROR_TOLERANCE);
		}
	}

	@Test
	public void testSimpleInterpolationWithValuesOutsideDefinedXValuesInterval() {
		DiscreteFunction<Integer, Double> xAxisFrom1to2 = xAxis(1, 2);

		for (int i = 3; i < 20; ++i) {
			Double interpolated = strategy.interpolate(i, xAxisFrom1to2);
			Double expected = INTEGER_TO_DOUBLE.apply(i);
			assertEquals(expected, interpolated, 0.0001);
		}
	}

	private DiscreteFunction<Integer, Double> xAxis(int from, int to) {
		MapBackedDiscreteFunction.Builder<Integer, Double> builder = MapBackedDiscreteFunction.builder();
		Double y1 = INTEGER_TO_DOUBLE.apply(from);
		Double y2 = INTEGER_TO_DOUBLE.apply(to);
		builder.put(from, y1);
		builder.put(to, y2);
		return builder.build();
	}

}
