package org.tensorics.core.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.function.interpolation.LinearInterpolationStrategy;
import org.tensorics.core.function.interpolation.SingleTypedLinearInterpolationStrategy;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Function;

/**
 * Unit tests for {@link MathFunctions}
 * 
 * @author caguiler
 */
public class MathFunctionsTest {

    private static final int LIMIT_OF_FIRST_DIMENSION = 4;
    private static final char LIMIT_OF_SECOND_DIMENSION = 'd';

    private static final Function<Integer, Double> INTEGER_TO_DOUBLE = new Function<Integer, Double>() {
        @Override
        public Double apply(Integer input) {
            return input.doubleValue();
        }
    };

    private static final Function<Double, Integer> DOUBLE_TO_INTEGER = new Function<Double, Integer>() {
        @Override
        public Integer apply(Double input) {
            return input.intValue();
        }
    };

    private static Tensor<Double> zeroDimensionalTensor;
    private Tensor<Double> oneDimensionalTensor;
    private DiscreteFunction<Integer, Double> discreteFunction;
    private ImmutableTensor<Boolean> twoDimensionalTensor;

    @Before
    public void setUp() {
        initOneDimensionalTensor();
        initTwoDimensionalTensor();
        zeroDimensionalTensor = ImmutableTensor.zeroDimensionalOf(0D);

        initDiscreteFunction();

    }

    /**
     * /@formatter:off
     * 
     * Initialises {@link #oneDimensionalTensor} as it follows:
     * 
     *       1   2   3   4
     *       
     *       1.0 2.0 3.0 4.0 
     *     
     *  /@formatter:on
     */
    private void initOneDimensionalTensor() {
        ImmutableTensor.Builder<Double> builder = ImmutableTensor.<Double> builder(Integer.class);

        //@formatter:off
        evenNumbersTo(LIMIT_OF_FIRST_DIMENSION)
                 .forEach(i-> builder
                 .putAt(INTEGER_TO_DOUBLE.apply(i), i));
        //@formatter:on

        oneDimensionalTensor = builder.build();
    }

    /**
     * /@formatter:off
     * 
     * Initialises {@link #twoDimensionalTensor} as it follows:
     * 
     *        a b c d 
     *       
     *     1  f f f f 
     *     2  f f f f
     *     3  f f f f
     *     4  f f f t
     *     
     *  Notice that: (4,d) is the only position set to true
     *     
     *  /@formatter:on
     */
    private void initTwoDimensionalTensor() {
        final ImmutableTensor.Builder<Boolean> builder = ImmutableTensor.<Boolean> builder(Integer.class,
                Character.class);

        for (int i = 1; i <= LIMIT_OF_FIRST_DIMENSION; ++i) {
            for (char c = 'a'; c < LIMIT_OF_SECOND_DIMENSION; ++c) {
                if (i != LIMIT_OF_FIRST_DIMENSION && c != LIMIT_OF_FIRST_DIMENSION) {
                    builder.putAt(false, Position.of(i, c));
                } else {
                    builder.putAt(true, Position.of(i, c));
                }
            }
        }

        twoDimensionalTensor = builder.build();
    }

    private void initDiscreteFunction() {
        MapBackedDiscreteFunction.Builder<Integer, Double> builder = MapBackedDiscreteFunction.builder();
        evenNumbersTo(LIMIT_OF_FIRST_DIMENSION).forEach(i -> builder.put(i, INTEGER_TO_DOUBLE.apply(i)));
        discreteFunction = builder.build();
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionFrom1DTensorWithNullTensorThrowsNPE() {
        MathFunctions.functionFrom1DTensor(null, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionFrom1DTensorWithNullDimensionClassThrowsNPE() {
        MathFunctions.functionFrom1DTensor(oneDimensionalTensor, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFunctionFrom1DTensorWithZeroDimensionalTensorThrowsIllegalArgumentException() {
        MathFunctions.functionFrom1DTensor(zeroDimensionalTensor, Object.class);
    }

    @Test
    public void testFunctionFrom1DTensorWithSimpleTensorReturnsExpectedFunction() {
        DiscreteFunction<Integer, Double> function = MathFunctions.functionFrom1DTensor(oneDimensionalTensor,
                Integer.class);
        assertEquals(discreteFunction, function);
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionsFromWithNullTensorThrowsNPE() {
        MathFunctions.functionsFrom(null, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionsFromWithNullDimensionClassThrowsNPE() {
        MathFunctions.functionsFrom(oneDimensionalTensor, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFunctionsFromWithZeroDimensionalTensorThrowsIllegalArgumentException() {
        MathFunctions.functionsFrom(zeroDimensionalTensor, Object.class);
    }

    @Test
    public void testFunctionsFromWithTwoDimensionalTensorReturnsExpectedTensorOfDiscreteFunctions() {
        Tensor<DiscreteFunction<Character, Boolean>> tensorFunction = MathFunctions.functionsFrom(twoDimensionalTensor,
                Character.class);

        assertEquals(1, tensorFunction.shape().dimensionality());

        for (int i = 1; i <= LIMIT_OF_FIRST_DIMENSION; ++i) {
            for (char c = 'a'; c < LIMIT_OF_SECOND_DIMENSION; ++c) {
                if (i != LIMIT_OF_FIRST_DIMENSION && c != LIMIT_OF_FIRST_DIMENSION) {
                    assertFalse(tensorFunction.get(i).apply(c));
                } else {
                    assertTrue(tensorFunction.get(i).apply(c));
                }
            }
        }

        assertFalse(tensorFunction.shape().dimensionSet().contains(Character.class));
        assertEquals(numbersTo(LIMIT_OF_FIRST_DIMENSION).collect(Collectors.toSet()),
                tensorFunction.shape().coordinatesOfType(Integer.class));
    }

    @Test(expected = NullPointerException.class)
    public void testSingleTypedWithNullConversionThrowsNPE() {
        MathFunctions.toSingleTyped(null, INTEGER_TO_DOUBLE);
    }

    @Test(expected = NullPointerException.class)
    public void testSingleTypedWithNullDiscreteFunctionThrowsNPE() {
        MathFunctions.toSingleTyped(null, null);
    }

    @Test
    public void testSingleTypedWithSimpleFunctionReturnsExpectedSingleTypedDiscreteFunction() {
        Set<Integer> expectedOldXValues = discreteFunction.definedXValues();

        //@formatter:off
        Set<Double> expectedYValues = discreteFunction.definedXValues().stream()
                .map(i->discreteFunction.apply(i)).collect(Collectors.toSet());
        //@formatter:on

        SingleTypedDiscreteFunction<Double> singleTypedFunction = MathFunctions.toSingleTyped(discreteFunction,
                INTEGER_TO_DOUBLE);

        Set<Integer> calculatedOldXValues = new HashSet<>();
        Set<Double> newYValues = new HashSet<>();

        for (Double x : singleTypedFunction.definedXValues()) {
            Integer oldXValue = DOUBLE_TO_INTEGER.apply(x);
            calculatedOldXValues.add(oldXValue);

            Double newYvalue = singleTypedFunction.apply(x);
            newYValues.add(newYvalue);
        }

        assertEquals(expectedOldXValues, calculatedOldXValues);
        assertEquals(expectedYValues, newYValues);
    }

    private Stream<Integer> evenNumbersTo(int to) {
        return numbersTo(to).filter(i -> i % 2 == 0);
    }

    private Stream<Integer> numbersTo(int to) {
        return IntStream.iterate(1, i -> i + 1).limit(to).boxed();
    }

    @Test(expected = NullPointerException.class)
    public void testInterpolatedWithNullSingleTypedDiscreteFunctionThrowsNPE() {
        MathFunctions.interpolated(null, new SingleTypedLinearInterpolationStrategy<>(Structures.doubles()));
    }

    @Test(expected = NullPointerException.class)
    public void testInterpolatedWithNullSingleTypedInterpolationStrategyThrowsNPE() {
        SingleTypedDiscreteFunction<Double> singleTyped = MapBackedSingleTypedDiscreteFunction.<Double> builder()
                .build();
        MathFunctions.interpolated(singleTyped, null);
    }

    @Test(expected = NullPointerException.class)
    public void testInterpolatedWithNullDiscreteFunctionThrowsNPE() {
        MathFunctions.interpolated(null, new LinearInterpolationStrategy(Structures.doubles(), DOUBLE_TO_INTEGER));
    }

    @Test(expected = NullPointerException.class)
    public void testInterpolatedWithNullInterpolationStrategyThrowsNPE() {
        MapBackedDiscreteFunction<Double, Double> function = MapBackedDiscreteFunction.<Double, Double> builder()
                .build();
        MathFunctions.interpolated(function, null);
    }

}
