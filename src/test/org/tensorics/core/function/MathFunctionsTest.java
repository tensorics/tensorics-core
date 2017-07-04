package org.tensorics.core.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.tensorics.core.tensor.Position.at;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
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

	private static final Function<Integer, Double> INTEGER_TO_DOUBLE = new Function<Integer, Double>() {
		@Override
		public Double apply(Integer input) {
			return input.doubleValue();
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

		zeroDimensionalTensor = Tensorics.scalarOf(0D);
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
		ImmutableTensor.Builder<Double> builder = ImmutableTensor.<Double>builder(Integer.class);

		//@formatter:off
        evenNumbersTo(LIMIT_OF_FIRST_DIMENSION)
                 .forEach(i-> builder
                 .put(at(i), INTEGER_TO_DOUBLE.apply(i)));
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
		final ImmutableTensor.Builder<Boolean> builder = ImmutableTensor.<Boolean>builder(Integer.class,
				Character.class);

		for (int i = 1; i <= LIMIT_OF_FIRST_DIMENSION; ++i) {
			for (char c = 'a'; c < LIMIT_OF_SECOND_DIMENSION; ++c) {
				if (i != LIMIT_OF_FIRST_DIMENSION && c != LIMIT_OF_FIRST_DIMENSION) {

					builder.put(Position.of(i, c), false);
				} else {
					builder.put(Position.of(i, c), true);
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
	public void testYValuesOfWithWithNullFunctionThrowsNPE() {
		MathFunctions.yValuesOf(null);
	}

	@Test
	public void testYValuesReturnsExpectedValuesOfCodomain() {
		Collection<Double> fCodomain = MathFunctions.yValuesOf(discreteFunction);
		List<Double> expectedCodomainValues = evenNumbersTo(LIMIT_OF_FIRST_DIMENSION).map(INTEGER_TO_DOUBLE::apply)
				.collect(Collectors.toList());
		assertEquals(expectedCodomainValues.size(), fCodomain.size());
		assertEquals(expectedCodomainValues, fCodomain);
	}

	private Stream<Integer> evenNumbersTo(int to) {
		return numbersTo(to).filter(i -> i % 2 == 0);
	}

	private Stream<Integer> numbersTo(int to) {
		return IntStream.iterate(1, i -> i + 1).limit(to).boxed();
	}

}
