// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.incubate.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Provides utility methods which deal with functions.
 * <p>
 * 
 * @author agorzaws
 */
public final class Functions {

    private static final double ZERO = 0.0;

    private Functions() {
        /* only static methods */
    }

    public static DiscreteFunction<Double, Double> convertToDiscreteFunctionFrom(double[] times, double[] values) {
        double[] errors = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            errors[i] = ZERO;
        }
        return convertToDiscreteFunctionFrom(times, values, errors);
    }

    public static List<Double> getXsIntersection(DiscreteFunction<Double, ?> first,
            DiscreteFunction<Double, ?> second) {

        List<Double> firstTs = first.getXs();
        List<Double> secondTs = second.getXs();

        List<Double> tsToReturn = new ArrayList<Double>();
        if (firstTs.size() == 0 || secondTs.size() == 0) {
            return tsToReturn;
        }

        double upperLimit = Math.min(firstTs.get(firstTs.size() - 1), secondTs.get(secondTs.size() - 1));
        double lowerLimit = Math.max(firstTs.get(0), secondTs.get(0));

        for (Double one : firstTs) {
            if (one > lowerLimit && one < upperLimit)
                tsToReturn.add(one);
        }
        return tsToReturn;
    }

    @SuppressWarnings("boxing")
    public static DiscreteFunction<Double, Double> convertToDiscreteFunctionFrom(double[] times, double[] values,
            double[] errors) {
        if (times.length != values.length && values.length != errors.length) {
            throw new IllegalArgumentException("Cannot create discrete function from two diffrent size arrays");
        }
        DiscreteFunctionBuilder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();
        for (int i = 0; i < times.length; i++) {
            builder.put(times[i], values[i], errors[i]);
        }
        return builder.withName("NoName").build();
    }

    public static DiscreteFunction<Double, Double> convertToDiscreteFunctionFrom(Map<Double, Double> map, String name) {
        DiscreteFunctionBuilder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();
        for (Entry<Double, Double> one : map.entrySet()) {
            builder.put(one.getKey(), one.getValue(), ZERO);
        }
        return builder.withInterpolationStrategy(new LinearInterpolationStrategy()).withName(name).build();
    }

    public static DiscreteFunction<Double, Double> emptyDiscreteFunction() {
        DiscreteFunctionBuilder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();
        return builder.withName("EMPTY").build();
    }

    public static DiscreteFunction<Double, Double> createInterpolatedFunctionFromTwoPoints(double startPoint,
            double endPoint, double length) {
        DiscreteFunctionBuilder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();
        builder.put(0.0, startPoint, ZERO);
        builder.put(length, endPoint, ZERO);
        return builder.withName("INTERPOLATED_TWO_POINTS").withInterpolationStrategy(new LinearInterpolationStrategy())
                .build();
    }

    /**
     * Multiplies the Y values of the given function with the value {@code scale}.
     * 
     * @param function the function to scale
     * @param scale the factor, by which to multiply the function Y values
     * @return the scaled function
     */
    public static DiscreteFunction<Double, Double> //
            scaleFunction(DiscreteFunction<Double, Double> function, double scale) {

        DiscreteFunctionBuilder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();
        builder.withName(function.getName());
        for (Double oneTime : function.getXs()) {
            builder.put(oneTime, function.getY(oneTime) * scale);
        }
        return builder.build();
    }

    /**
     * Performs numerical integral of given function within full range of given function. Simplest method of sum of
     * dt*Y.
     * 
     * @param function the function to treat
     * @return the value of the numerical integral
     */
    public static double integrate(DiscreteFunction<Double, Double> function) {
        List<Double> xValues = function.getXs();
        double firstTime = xValues.get(0);
        double endTime = xValues.get(xValues.size() - 1);
        return integrate(function, firstTime, endTime);
    }

    /**
     * performs numerical integral of given function within start and end values. Simplest method of sum of dt*Y.
     * 
     * @param function the function to integrate
     * @param start the lower border of the integration
     * @param end the upper border of the integration
     * @return the value calculated from the integration
     */
    public static double integrate(DiscreteFunction<Double, Double> function, double start, double end) {
        double sum = 0.0;
        List<Double> xValues = function.getXs();
        double lastT = start;
        for (Double t : xValues) {
            if (t >= start && t <= end) {
                sum += function.getY(t) * (t - lastT);
                lastT = t;
            }
        }
        return sum;
    }

    /**
     * Allows to get cut first part of ig exp decay if above limit.
     * 
     * @param function function to treat
     * @param yLimit UPPER limit, above which original values are omitted
     * @return the cut function
     */
    public static DiscreteFunction<Double, Double> cutBeginingOfExp(DiscreteFunction<Double, Double> function,
            double yLimit) {
        DiscreteFunctionBuilder<Double, Double> builder = SortedMapBackedDiscreteFunction.builder();

        for (Double x : function.getXs()) {
            if (function.getY(x) <= yLimit) {
                builder.put(x, function.getY(x));
            }
        }

        return builder.build();
    }
}
