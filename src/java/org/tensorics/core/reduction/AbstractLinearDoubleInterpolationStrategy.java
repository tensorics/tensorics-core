/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction;

import java.util.Comparator;
import java.util.List;

import org.tensorics.core.tensor.Tensor;

/**
 * An abstract implementation for the linear interpolation of the {@link Double}
 * {@link Tensor}.
 * <p>
 * It returns a value based on the ratio in the comparable (ie. time) domain,
 * between the reference point(t_i) and the pair of the one before(t_1) and
 * after(t_2) like: <br>
 * <br>
 * ratio = (t_i - t_1)/(t_2 - t_1)
 * <p>
 * The final interpolated value is: v_i = v(t_1)+(v(t_2)-v(t_1))*ratio.
 * 
 * @author agorzaws
 * @param <C>
 *            type of the coordinate, must be the {@link Comparable}
 */
public abstract class AbstractLinearDoubleInterpolationStrategy<C> extends AbstractInterpolationStrategy<C, Double> {

	public AbstractLinearDoubleInterpolationStrategy(Comparator<C> comparator) {
		super(comparator);
	}

	@Override
	public Double getInterpolatedValue(Tensor<Double> tensorWithTheOnlyOneCoordinateOfC, C coordineteToInterpolate) {

		List<C> orderedList = getOrderedListOfComparableCoodrinate(tensorWithTheOnlyOneCoordinateOfC,
				coordineteToInterpolate);
		C thePreviousComparable = findIndex(orderedList, coordineteToInterpolate, 0);
		C theNextComparable = findIndex(orderedList, coordineteToInterpolate, 1);

		Double firstPoint = tensorWithTheOnlyOneCoordinateOfC.get(thePreviousComparable);
		return firstPoint + (tensorWithTheOnlyOneCoordinateOfC.get(theNextComparable) - firstPoint)
				* ratio(thePreviousComparable, theNextComparable, coordineteToInterpolate);
	}

	public abstract double ratio(C previousComparable, C nextComparable, C value);

}
