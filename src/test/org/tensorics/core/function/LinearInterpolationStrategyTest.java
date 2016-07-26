package org.tensorics.core.function;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.operations.Conversions;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.function.interpolation.LinearInterpolationStrategy;

/**
 * <p>
 * Tests that {@link LinearInterpolationStrategy} calculates interpolations properly.
 * <p>
 * <b>Notice:</b> In this test suite only {@link Structures#doubles()} is taken into account as mathematical field.
 * 
 * @author caguiler
 */
public class LinearInterpolationStrategyTest {

    private static final Conversion<Double, Double> IDENTITY = Conversions.identity();
    private static final Comparator<Double> COMPARATOR = Double::compareTo;

    private static final double ERROR_TOLERANCE = 0.000001;

    private LinearInterpolationStrategy<Double> strategy;

    @Before
    public void setUp() {
        strategy = new LinearInterpolationStrategy<>(Structures.doubles());
    }

    @Test(expected = NullPointerException.class)
    public void testInterpolationWithNullxValueThrowsNPE() {
        strategy.interpolate(null, xAxis(0, 1), IDENTITY, COMPARATOR);
    }

    @Test(expected = NullPointerException.class)
    public void testInterpolationWithNullFunctionThrowsNPE() {
        strategy.interpolate(1.0, null, IDENTITY, COMPARATOR);
    }

    @Test(expected = NullPointerException.class)
    public void testInterpolationWithConversionThrowsNPE() {
        strategy.interpolate(1.0, xAxis(0, 1), null, COMPARATOR);
    }

    @Test(expected = IllegalStateException.class)
    public void testInterpolationWithFunctionThatContainsOnlyOneXValueThrowsIllegalStateException() {
        strategy.interpolate(33.0, functionWithOnlyOneXValue(), IDENTITY, COMPARATOR);
    }

    @Test
    public void testSimpleInterpolationWithValuesWithinDefinedXValuesInterval() {
        int from = 1;
        int to = 20;
        DiscreteFunction<Double, Double> xAxisFrom1to20 = xAxis(from, to);

        for (double i = from + 1; i < to; ++i) {
            Double interpolated = strategy.interpolate(i, xAxisFrom1to20, IDENTITY, COMPARATOR);
            Double expected = i;
            assertEquals(expected, interpolated, ERROR_TOLERANCE);
        }
    }

    @Test
    public void testSimpleInterpolationWithValuesOutsideDefinedXValuesInterval() {
        int from = 1;
        int to = 2;
        DiscreteFunction<Double, Double> xAxisFrom1to2 = xAxis(from, to);

        for (double i = from + 1; i < 20 * to; ++i) {
            Double interpolated = strategy.interpolate(i, xAxisFrom1to2, IDENTITY, COMPARATOR);
            Double expected = i;
            assertEquals(expected, interpolated, ERROR_TOLERANCE);
        }
    }

    @Test
    public void testFancyInterpolationWithValuesWithinDefinedXValuesInterval() {
        DiscreteFunction<Double, Double> function = createComplexDiscreteFunction();

        Double interpolatedValue;

        interpolatedValue = strategy.interpolate(1.5, function, IDENTITY, COMPARATOR);
        assertEquals(1.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(2.5, function, IDENTITY, COMPARATOR);
        assertEquals(1.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(3.5, function, IDENTITY, COMPARATOR);
        assertEquals(2, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(4.5, function, IDENTITY, COMPARATOR);
        assertEquals(2, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(5.5, function, IDENTITY, COMPARATOR);
        assertEquals(2.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(6.5, function, IDENTITY, COMPARATOR);
        assertEquals(2.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(6.5, function, IDENTITY, COMPARATOR);
        assertEquals(2.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(8.0, function, IDENTITY, COMPARATOR);
        assertEquals(3.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(9.5, function, IDENTITY, COMPARATOR);
        assertEquals(3.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(9.5, function, IDENTITY, COMPARATOR);
        assertEquals(3.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(9.9, function, IDENTITY, COMPARATOR);
        assertEquals(1.5, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(11.0, function, IDENTITY, COMPARATOR);
        assertEquals(1.0, interpolatedValue, ERROR_TOLERANCE);

        interpolatedValue = strategy.interpolate(12.0, function, IDENTITY, COMPARATOR);
        assertEquals(1.0, interpolatedValue, ERROR_TOLERANCE);
    }

    @Test
    public void testFancyInterpolationWithValuesOutsideDefinedXValuesInterval() {
        DiscreteFunction<Double, Double> function = createComplexDiscreteFunction();

        Double interpolatedValue;

        for (double i = 0; i > -20; i--) {
            interpolatedValue = strategy.interpolate(i, function, IDENTITY, COMPARATOR);
            assertEquals(i, interpolatedValue, ERROR_TOLERANCE);
        }

        for (double i = 15; i < 35; i++) {
            interpolatedValue = strategy.interpolate(15.0, function, IDENTITY, COMPARATOR);
            assertEquals(1.0, interpolatedValue, ERROR_TOLERANCE);
        }
    }

    private DiscreteFunction<Double, Double> createComplexDiscreteFunction() {
        MapBackedDiscreteFunction.Builder<Double, Double> builder = MapBackedDiscreteFunction.builder();

        double[] xAxis = { 1, 2, 3, 4, 5, 6, 7, 9, 10, 13 };
        double[] yAxis = { 1, 2, 1, 3, 1, 4, 1, 6, 1, 1 };

        for (int i = 0; i < yAxis.length; i++) {
            builder.put(xAxis[i], yAxis[i]);
        }

        return builder.build();
    }

    private DiscreteFunction<Double, Double> xAxis(double from, double to) {
        MapBackedDiscreteFunction.Builder<Double, Double> builder = MapBackedDiscreteFunction.builder();
        Double y1 = from;
        Double y2 = to;
        builder.put(from, y1);
        builder.put(to, y2);
        return builder.build();
    }

    private DiscreteFunction<Double, Double> functionWithOnlyOneXValue() {
        MapBackedDiscreteFunction.Builder<Double, Double> builder = MapBackedDiscreteFunction.builder();
        builder.put(1D, 1D);
        return builder.build();
    }

}
