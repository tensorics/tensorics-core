/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

public class FieldInterpolator<C, V> extends ScalarSupport<V> {

    private Collection<C> coordinates;
    private Function<C, V> xConversion;
    private Function<C, V> yFunction;
    private C x;

    public FieldInterpolator(ExtendedField<V> field, Collection<C> coordinates, Function<C, V> xConversion,
            Function<C, V> yFunction, C x) {
        super(field);
        this.coordinates = coordinates;
        this.xConversion = xConversion;
        this.yFunction = yFunction;
        this.x = x;
    }

    public V calculate() {
        return findNeigbouringX().interpolate();
    }

    private X1X2Pair findNeigbouringX() {
        int size = coordinates.size();
        
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

        return new X1X2Pair(x1, x2);
    }

    private class X1X2Pair {
        private final C x1;
        private final C x2;

        private X1X2Pair(C x1, C x2) {
            this.x1 = x1;
            this.x2 = x2;
        }

        V interpolate() {
            V xMinusX1 = calculate(_x(x)).minus(_x(x1));
            V x2MinusX1 = calculate(_x(x2)).minus(_x(x1));
            V division = calculate(xMinusX1).dividedBy(x2MinusX1);

            V y2MinusY1 = calculate(_y(x2)).minus(_y(x1));
            V partialResult = calculate(division).times(y2MinusY1);
            return calculate(partialResult).plus(_y(x1));

        }

        private V _x(C xx) {
            return xConversion.apply(xx);
        }

        private V _y(C xx) {
            return yFunction.apply(xx);
        }
    }

}
