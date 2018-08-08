/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample.impl;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.Function;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.resample.SingleDimensionResampler;

/**
 * Resamples in one dimension, by linearly interpolating by two neighboring values. If possible it will try to
 * interpolate between the left and right neighbor. If The requested point is before the first support coordinate or
 * after the last one, then the interpolation will be done from the first two points or the last two points,
 * respectively.
 * 
 * @author kfuchsbe
 * @param <C> the type of the coordinates of the concerned dimension
 * @param <V> the type of the tensor values to resample
 */
public class LinearInterpolationResampler<C, V> implements SingleDimensionResampler<C, V> {

    private final ExtendedField<V> field;
    private final Function<C, V> xConversion;

    public LinearInterpolationResampler(ExtendedField<V> field, Function<C, V> toFieldElementConversion) {
        this.field = requireNonNull(field, "field must not be null");
        this.xConversion = requireNonNull(toFieldElementConversion, "conversion to field element must not be null");
    }

    @Override
    public V resample(Set<C> coordinates, Function<C, V> yFunction, C coordinate) {
        if (coordinates.contains(coordinate)) {
            return yFunction.apply(coordinate);
        }
        return new FieldInterpolator<>(field, coordinates, xConversion, yFunction, coordinate).calculate();
    }

    @Override
    public boolean canResample(Set<C> coordinates, C coordinate) {
        /*
         * this is not necessarily always true ... It could be that several coordinates transform into the same V ... to
         * be seen if this shall be considered..
         */
        return (coordinates.size() >= 2) || (coordinates.contains(coordinate));
    }

}
