package org.tensorics.core.function.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.operations.Conversions;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.MapBackedDiscreteFunction;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.ManipulationOptions;

import com.google.common.collect.Sets;

/**
 * Tests the behavior of {@link FunctionSupport}
 * 
 * @author caguiler
 */
public class FunctionSupportTest {

    private static final Comparator<Double> COMPARATOR = Double::compareTo;
    private DiscreteFunction<Double, Double> two;
    private DiscreteFunction<Double, Double> three;
    private DiscreteFunction<Double, Double> complex;

    private Conversion<Double, Double> conversion = Conversions.identity();

    private FunctionSupportWithConversionAndComparator<Double, Double> supportWithConversion;
    private FunctionSupport<Double> functionSupport;

    @Before
    public void setUp() {
        MapBackedDiscreteFunction.Builder<Double, Double> builder2 = MapBackedDiscreteFunction.builder();
        MapBackedDiscreteFunction.Builder<Double, Double> builder3 = MapBackedDiscreteFunction.builder();
        MapBackedDiscreteFunction.Builder<Double, Double> builder4 = MapBackedDiscreteFunction.builder();

        for (double i = 1; i < 5; i += 2) {
            builder2.put(i, 2D);
            builder3.put(i + 1, 3D);
        }

        two = builder2.build();
        three = builder3.build();

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
        complex = builder4.build();

        functionSupport = new FunctionSupport<Double>(
                EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));

        supportWithConversion = functionSupport.withConversionAndComparator(conversion, COMPARATOR);

    }

    @Test
    public void testPlus() {
        DiscreteFunction<Double, Double> five = supportWithConversion.calculate(three).plus(two);
        DiscreteFunction<Double, Double> cinco = supportWithConversion.calculate(two).plus(three);

        assertTrue(five.definedXValues().stream().map(five::apply).allMatch(y -> y == 5));
        assertEquals(five.definedXValues(), Sets.union(two.definedXValues(), three.definedXValues()));
        assertEquals(five, cinco);
    }

    @Test
    public void testMinus() {
        DiscreteFunction<Double, Double> one = supportWithConversion.calculate(three).minus(two);
        DiscreteFunction<Double, Double> minusOne = supportWithConversion.calculate(three).minus(three);

        assertTrue(one.definedXValues().stream().map(one::apply).allMatch(y -> y == 1));
        assertEquals(one.definedXValues(), Sets.union(two.definedXValues(), three.definedXValues()));
        assertNotEquals(one, minusOne);
    }

    @Test
    public void testTimes() {
        DiscreteFunction<Double, Double> nine = supportWithConversion.calculate(three).times(three);

        assertTrue(nine.definedXValues().stream().map(nine::apply).allMatch(y -> y == 9));
        assertEquals(nine.definedXValues(), three.definedXValues());
    }

    @Test
    public void testDivideBy() {
        DiscreteFunction<Double, Double> threeHalfs = supportWithConversion.calculate(three).dividedBy(two);
        DiscreteFunction<Double, Double> twoThirds = supportWithConversion.calculate(two).dividedBy(three);

        assertTrue(threeHalfs.definedXValues().stream().map(threeHalfs::apply).allMatch(y -> y == 3 / 2.));
        assertEquals(threeHalfs.definedXValues(), Sets.union(two.definedXValues(), three.definedXValues()));
        assertTrue(twoThirds.definedXValues().stream().map(twoThirds::apply).allMatch(y -> y == 2 / 3.));
        assertNotEquals(threeHalfs, twoThirds);
    }

    @Test
    public void testAverage() {
        Double average = supportWithConversion.averageOf(three);
        assertEquals(3, average, Double.MIN_VALUE);
    }

    @Test
    public void testRms() {
        Double rms = supportWithConversion.rmsOf(three);
        assertEquals(3, rms, Double.MIN_VALUE);
    }

    @Test
    public void testStd() {
        Double dos = supportWithConversion.stdOf(complex);
        assertEquals(2, dos, Double.MIN_VALUE);

        Double zero = supportWithConversion.stdOf(two);
        assertEquals(0, zero, Double.MIN_VALUE);
    }

}
