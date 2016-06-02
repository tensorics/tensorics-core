package org.tensorics.core.function;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Tensor;

/**
 * Unit tests for {@link MathFunctions}
 * 
 * @author caguiler
 *
 */
public class MathFunctionsTest {

	private static Tensor<Double> ZERO_DIMENSIONAL_TENSOR = ImmutableTensor.zeroDimensionalOf(0D);

	@Test(expected = NullPointerException.class)
	public void testFunctionFrom1DTensorWithNullTensor() {
		MathFunctions.functionFrom1DTensor(null, Object.class);
	}

	@Test(expected = NullPointerException.class)
	public void testFunctionFrom1DTensorWithDimensionClas() {
		MathFunctions.functionFrom1DTensor(ZERO_DIMENSIONAL_TENSOR, null);
	}

	@Test
	public void testFunctionFrom1DTensorWithSimpleTensorReturnsExpectedFunction() {
		ImmutableTensor.Builder<Double> builder = ImmutableTensor.<Double> builder(Integer.class);

		//@formatter:off
		evenNumbersTo(20)
				 .forEach(i-> builder.putAt(i.doubleValue(), i));
		//@formatter:on
		
		Tensor<Double> simpleTensor = builder.build();

		DiscreteFunction<Integer, Double> function = MathFunctions.functionFrom1DTensor(simpleTensor,
				Integer.class);

		Set<Integer> expectedDefinedValues = evenNumbersTo(20).collect(Collectors.toSet());
		assertEquals(function.definedXValues(), expectedDefinedValues);
		
		for (Integer x : expectedDefinedValues) {
			assertTrue(function.apply(x).equals(x.doubleValue()));
		}

	}

	private Stream<Integer> evenNumbersTo(int to) {
		return IntStream.iterate(1, i -> i + 1).filter(i -> i % 2 == 0).limit(to).boxed();
	}
}
