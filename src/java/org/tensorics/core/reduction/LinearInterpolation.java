package org.tensorics.core.reduction;

import java.util.List;
import java.util.function.Function;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;
import org.tensorics.core.tensor.Tensor;

/**
 * Generalized linear interpolation, provided a field for the values of a
 * tensor, and a mapper from the coordinate to interpolate to the value.
 * 
 * @author mihostet
 *
 * @param <C> the coordinate type
 * @param <V> the value type
 */
public class LinearInterpolation<C, V> extends AbstractInterpolationStrategy<C, V> {

	private final Function<C, V> fieldMapper;
	private final ScalarSupport<V> support;

	public LinearInterpolation(ExtendedField<V> field, Function<C, V> fieldMapper) {
		super((c1, c2) -> field.comparator().compare(fieldMapper.apply(c1), fieldMapper.apply(c2)));
		this.support = new ScalarSupport<>(field);
		this.fieldMapper = fieldMapper;
	}

	@Override
	public V getInterpolatedValue(Tensor<V> tensorWithTheOnlyOneCoordinateOfC, C coordinateToInterpolate) {

		List<C> orderedList = getOrderedListOfComparableCoordinate(tensorWithTheOnlyOneCoordinateOfC,
				coordinateToInterpolate);
		C thePreviousComparable = findIndex(orderedList, coordinateToInterpolate, 0);
		C theNextComparable = findIndex(orderedList, coordinateToInterpolate, 1);

		V previousPoint = tensorWithTheOnlyOneCoordinateOfC.get(thePreviousComparable);
		V nextPoint = tensorWithTheOnlyOneCoordinateOfC.get(theNextComparable);

		V previousCoordinate = fieldMapper.apply(thePreviousComparable);
		V actualCoordinate = fieldMapper.apply(coordinateToInterpolate);
		V nextCoordinate = fieldMapper.apply(theNextComparable);

		V ratio = support.calculate(support.calculate(actualCoordinate).minus(previousCoordinate))
				.dividedBy(support.calculate(nextCoordinate).minus(previousCoordinate));

		V offset = support.calculate(support.calculate(nextPoint).minus(previousPoint)).times(ratio);

		return support.calculate(previousPoint).plus(offset);
	}
}
