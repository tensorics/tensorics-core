/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample.impl;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * Takes a discrete set of x-values (being of type C - one coordinate of a tensor) and a transformation function of C to
 * V together with a callback function to Y values (of type V - the elements of a field) corresponding to the given X
 * values, and tries to determine a y-value for a given x-value by interpolating between the two neighbouring points.
 * The main purpose of this class is encapsulation for reusage and avoiding to avoid pass around a lot of parameters.
 * 
 * @author kfuchsbe
 * @param <C> the type of the coordinate (x-values)
 * @param <V> the type of the field elements (y-values of the 1-D discrete function to interpolate
 */
public class FieldInterpolator<C, V> extends ScalarSupport<V> {

    private Collection<C> coordinates;
    private Function<C, V> xConversion;
    private Function<C, V> yFunction;
    private C x;

    /**
     * Constructor, taking all the parameters required for the calculation
     * 
     * @param field the field of elements of type V, which is required to do the calculation for the interpolation and
     *            to have a comparator available.
     * @param coordinates the discrete set of coordinates, which represent the support points for the interpolation
     * @param xConversion a conversion function to convert x-values into the type V. This function has to be defined for
     *            each value of coordinates and x.
     * @param yFunction the function providing the y-values corresponding to each x-value. This function has to be
     *            defined for each value of coordinates (not for x, as otherwise interpolation would not be required).
     * @param x the point at which the y-value shall be determined.
     * @throws NullPointerException in case any of the given parameters is {@code null}
     */
    public FieldInterpolator(ExtendedField<V> field, Collection<C> coordinates, Function<C, V> xConversion,
            Function<C, V> yFunction, C x) {
        super(field);
        this.coordinates = requireNonNull(coordinates, "coordinates must not be null");
        this.xConversion = requireNonNull(xConversion, "XConversion must not be null");
        this.yFunction = requireNonNull(yFunction, "yFunction must not be null");
        this.x = requireNonNull(x, "x must not be null");
    }

    /**
     * Performs the actual calculation of interpolation.
     * 
     * @return the interpolated value at x
     */
    public V calculate() {
        return findNeigbouringX().interpolate();
    }

    private TwoPointLinearInterpolation findNeigbouringX() {
        int size = coordinates.size();
        if (size < 2) {
            throw new NoSuchElementException(
                    "Interpolation is not possible with less than 2 support points (" + size + " available).");
        }

        List<C> xValues = new ArrayList<>(coordinates);
        Comparator<C> comparator = Comparator.comparing(xConversion, field().comparator());

        Collections.sort(xValues, comparator);

        C firstX = xValues.get(0);
        C lastX = xValues.get(size - 1);

        boolean lessOrEqualThanFirstX = comparator.compare(x, firstX) <= 0;
        boolean greaterOrEqualThanLastX = comparator.compare(x, lastX) >= 0;

        C x1 = null;
        C x2 = null;

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

        return new TwoPointLinearInterpolation(x1, x2);
    }

    private class TwoPointLinearInterpolation {
        private final C x1;
        private final C x2;

        private TwoPointLinearInterpolation(C x1, C x2) {
            this.x1 = x1;
            this.x2 = x2;
        }

        private V interpolate() {
            V xMinusX1 = calculate(x(x)).minus(x(x1));
            V x2MinusX1 = calculate(x(x2)).minus(x(x1));
            V division = calculate(xMinusX1).dividedBy(x2MinusX1);

            V y2MinusY1 = calculate(y(x2)).minus(y(x1));
            V partialResult = calculate(division).times(y2MinusY1);
            return calculate(partialResult).plus(y(x1));

        }

        private V x(C xx) {
            return xConversion.apply(xx);
        }

        private V y(C xx) {
            return yFunction.apply(xx);
        }
    }

}
