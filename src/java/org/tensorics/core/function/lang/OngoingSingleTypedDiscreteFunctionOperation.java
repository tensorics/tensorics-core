package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.MapBackedSingleTypedDiscreteFunction;
import org.tensorics.core.function.MathFunctions;
import org.tensorics.core.function.SingleTypedDiscreteFunction;
import org.tensorics.core.function.SingleTypedInterpolatedFunction;
import org.tensorics.core.function.interpolation.SingleTypedLinearInterpolationStrategy;
import org.tensorics.core.math.operations.BinaryOperation;

import com.google.common.collect.Sets;

public class OngoingSingleTypedDiscreteFunctionOperation<Y extends Comparable<? super Y>> {

	private final Environment<Y> environment;
	private final SingleTypedDiscreteFunction<Y> left;

	public OngoingSingleTypedDiscreteFunctionOperation(Environment<Y> environment,
			SingleTypedDiscreteFunction<Y> left) {
		this.environment = environment;
		this.left = left;
	}

	public SingleTypedDiscreteFunction<Y> plus(SingleTypedDiscreteFunction<Y> right) {
		return applyOperation(right, environment.field().addition());
	}

	public SingleTypedDiscreteFunction<Y> minus(SingleTypedDiscreteFunction<Y> right) {
		return applyOperation(right, environment.field().subtraction());
	}

	public SingleTypedDiscreteFunction<Y> times(SingleTypedDiscreteFunction<Y> right) {
		return applyOperation(right, environment.field().multiplication());
	}

	public SingleTypedDiscreteFunction<Y> dividedBy(SingleTypedDiscreteFunction<Y> right) {
		return applyOperation(right, environment.field().division());
	}

	private SingleTypedDiscreteFunction<Y> applyOperation(SingleTypedDiscreteFunction<Y> right,
			BinaryOperation<Y> operation) {
		@SuppressWarnings("unchecked")
		SingleTypedLinearInterpolationStrategy<Y> strategy = new SingleTypedLinearInterpolationStrategy<Y>(
				environment.field());
		SingleTypedInterpolatedFunction<Y> rigthInterpolated = MathFunctions.interpolated(right, strategy);
		SingleTypedInterpolatedFunction<Y> leftInterpolated = MathFunctions.interpolated(left, strategy);

		MapBackedSingleTypedDiscreteFunction.Builder<Y> builder = MapBackedSingleTypedDiscreteFunction.builder();

		for (Y x : Sets.union(left.definedXValues(), right.definedXValues())) {

			Y y1 = leftInterpolated.apply(x);
			Y y2 = rigthInterpolated.apply(x);

			Y result = operation.perform(y1, y2);

			builder.put(x, result);
		}

		return builder.build();
	}
}
