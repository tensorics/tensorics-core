package org.tensorics.core.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

/**
 * Unit tests for {@link MathFunctions}
 * 
 * @author caguiler
 */
public class MathFunctionsTest {

    private static final int LIMIT_OF_FIRST_DIMENSION = 4;
    private static final char LIMIT_OF_SECOND_DIMENSION = 'd';
    private static Tensor<Double> ZERO_DIMENSIONAL_TENSOR;
    private Tensor<Double> ONE_DIMENSIONAL_TENSOR;
    private ImmutableTensor<Boolean> TWO_DIMENSIONAL_TENSOR;

    @Before
    public void setUp() {
        initOneDimensionalTensor();
        initTwoDimensionalTensor();
        ZERO_DIMENSIONAL_TENSOR = ImmutableTensor.zeroDimensionalOf(0D);
    }

    /**
     * /@formatter:off
     * 
     * Initialises {@link #ONE_DIMENSIONAL_TENSOR} as it follows:
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
                 .putAt(i.doubleValue(), i));
        //@formatter:on

        ONE_DIMENSIONAL_TENSOR = builder.build();
    }

    /**
     * /@formatter:off
     * 
     * Initialises {@link #TWO_DIMENSIONAL_TENSOR} as it follows:
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

        TWO_DIMENSIONAL_TENSOR = builder.build();
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionFrom1DTensorWithNullTensorThrowsNPE() {
        MathFunctions.functionFrom1DTensor(null, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionFrom1DTensorWithNullDimensionClassThrowsNPE() {
        MathFunctions.functionFrom1DTensor(ONE_DIMENSIONAL_TENSOR, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFunctionFrom1DTensorWithZeroDimensionalTensorThrowsIllegalArgumentException() {
        MathFunctions.functionFrom1DTensor(ZERO_DIMENSIONAL_TENSOR, Object.class);
    }

    @Test
    public void testFunctionFrom1DTensorWithSimpleTensorReturnsExpectedFunction() {
        DiscreteFunction<Integer, Double> function = MathFunctions.functionFrom1DTensor(ONE_DIMENSIONAL_TENSOR,
                Integer.class);

        //@formatter:off
        Set<Integer> expectedDefinedValues = evenNumbersTo(LIMIT_OF_FIRST_DIMENSION)
                .collect(Collectors.toSet());
        //@formatter:on

        assertEquals(function.definedXValues(), expectedDefinedValues);

        for (Integer x : expectedDefinedValues) {
            assertTrue(function.apply(x).equals(x.doubleValue()));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionsFromWithNullTensorThrowsNPE() {
        MathFunctions.functionsFrom(null, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionsFromWithNullDimensionClassThrowsNPE() {
        MathFunctions.functionsFrom(ONE_DIMENSIONAL_TENSOR, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFunctionsFromWithZeroDimensionalTensorThrowsIllegalArgumentException() {
        MathFunctions.functionsFrom(ZERO_DIMENSIONAL_TENSOR, Object.class);
    }

    @Test
    public void testFunctionsFromWithTwoDimensionalTensorReturnsExpectedTensorOfDiscreteFunctions() {
        Tensor<DiscreteFunction<Character, Boolean>> tensorFunction = MathFunctions
                .functionsFrom(TWO_DIMENSIONAL_TENSOR, Character.class);

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

        assertEquals(numbersTo(LIMIT_OF_FIRST_DIMENSION).collect(Collectors.toSet()),
                tensorFunction.shape().coordinatesOfType(Integer.class));
    }

    private Stream<Integer> evenNumbersTo(int to) {
        return numbersTo(to).filter(i -> i % 2 == 0);
    }

    private Stream<Integer> numbersTo(int to) {
        return IntStream.iterate(1, i -> i + 1).limit(to).boxed();
    }

}
