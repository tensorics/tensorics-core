/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample.impl;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.function.Function;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.resample.SingleDimensionResampler;

public class LinearInterpolationResampler<C, V> implements SingleDimensionResampler<C, V> {

    private final ExtendedField<V> field;
    private final Function<C, V> xConversion;

    public LinearInterpolationResampler(ExtendedField<V> field, Function<C, V> toFieldElementConversion) {
        this.field = requireNonNull(field, "field must not be null");
        this.xConversion = requireNonNull(toFieldElementConversion, "conversion to field element must not be null");
    }

    @Override
    public V resample(Collection<C> coordinates, Function<C, V> yFunction, C coordinate) {
        return new FieldInterpolator<>(field, coordinates, xConversion, yFunction, coordinate).calculate();
    }

    @Override
    public boolean canResample(Collection<C> coordinates, C coordinate) {
        /*
         * this is not necessarily always true ... It could be that several coordinates transform into the same V ... to
         * be seen if this shall be considered..
         */
        return coordinates.size() >= 2;
    }

}
