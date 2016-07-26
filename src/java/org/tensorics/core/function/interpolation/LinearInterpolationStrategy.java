package org.tensorics.core.function.interpolation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

import com.google.common.base.Preconditions;

/**
 * An {@link InterpolationStrategy} for interpolating linearly {@link DiscreteFunction}s.
 * 
 * @see InterpolationStrategy
 * @author agorzaws, caguiler
 */
public class LinearInterpolationStrategy<Y> extends ScalarSupport<Y> implements InterpolationStrategy<Y> {

    private static final long serialVersionUID = 1L;

    public LinearInterpolationStrategy(ExtendedField<Y> field) {
        super(field);
    }

    @Override
    public <X> Y interpolate(X x, DiscreteFunction<X, Y> function, Conversion<X, Y> conversion,
            Comparator<X> comparator) {
        Preconditions.checkNotNull(x, "x cannot be null!");
        Preconditions.checkNotNull(function, "function cannot be null!");
        Preconditions.checkNotNull(conversion, "conversion cannot be null!");

        int size = function.definedXValues().size();

        if (size < 2) {
            throw new IllegalStateException("Cannot interpolate function with less than 2 <X>-values defined!");
        }

        List<X> xValues = new ArrayList<>(function.definedXValues());

        Collections.sort(xValues, comparator);

        X firstX = xValues.get(0);
        X lastX = xValues.get(size - 1);

        boolean lessOrEqualThanFirstX = comparator.compare(x, firstX) <= 0;
        boolean greaterOrEqualThanLastX = comparator.compare(x, lastX) >= 0;

        X x1 = null;
        X x2 = null;

        if (lessOrEqualThanFirstX) {
            x1 = firstX;
            x2 = xValues.get(1);
        } else if (greaterOrEqualThanLastX) {
            x1 = xValues.get(size - 2);
            x2 = lastX;
        } else {
            int index = 0;

            do {
                x2 = xValues.get(index);
                ++index;
            } while (comparator.compare(x2, x) < 0);

            x1 = xValues.get(index - 2);
        }

        Y xInYDomain = conversion.apply(x);
        Y x1InyDomain = conversion.apply(x1);
        Y x2InYDomain = conversion.apply(x2);

        Y y1 = function.apply(x1);
        Y y2 = function.apply(x2);

        return calculateInterpolation(xInYDomain, x1InyDomain, x2InYDomain, y1, y2);
    }

    private Y calculateInterpolation(Y x, Y x1, Y x2, Y y1, Y y2) {
        Y xMinusX1 = calculate(x).minus(x1);

        Y x2MinusX1 = calculate(x2).minus(x1);

        Y division = calculate(xMinusX1).dividedBy(x2MinusX1);

        Y y2MinusY1 = calculate(y2).minus(y1);

        Y partialResult = calculate(division).times(y2MinusY1);

        return calculate(partialResult).plus(y1);
    }

}
