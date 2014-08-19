/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

import java.util.List;

/**
 * A strategy for interpolating linearly between two values of doubles, with also the x-direction of the function being
 * double values.
 * 
 * @author agorzaws
 */
public class LinearInterpolationStrategy implements InterpolationStrategy<Double, Double> {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("boxing")
    @Override
    public Double interpolate(Double xValue, DiscreteFunction<Double, Double> function) {
        double timeBefore = 0;
        double timeAfter = 0;
        boolean nextBreak = false;
        List<Double> timesInFunction = function.getXs();

        int size = timesInFunction.size();
        if (size < 1) {
            throw new IllegalStateException(
                    "Cannot interpolate beyond function time series for only one or none points!");
        }

        Double lastTime = timesInFunction.get(size - 1);
        Double firstTime = timesInFunction.get(0);
        if (xValue < firstTime || xValue > lastTime) {
            throw new IllegalStateException("Cannot interpolate beyond function time series [" + firstTime + ", "
                    + lastTime + "]" + " asked for " + xValue);
        }

        for (Double xVal : timesInFunction) {
            if (nextBreak) {
                break;
            }
            if (xVal >= xValue) {
                timeAfter = xVal;
                nextBreak = true;
            }
        }
        timeBefore = timesInFunction.get(timesInFunction.indexOf(timeAfter) - 1);

        return function.getY(timeBefore) + (Math.abs(xValue - timeBefore) / Math.abs(timeAfter - timeBefore))
                * (function.getY(timeAfter) - function.getY(timeBefore));
    }
}
