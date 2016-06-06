/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.interpolation;

import org.tensorics.core.function.SingleTypedDiscreteFunction;
import org.tensorics.core.math.ExtendedField;

import com.google.common.base.Function;

/**
 * An {@link InterpolationStrategy} for interpolating linearly {@link SingleTypedDiscreteFunction}
 * 
 * @author caguiler
 * @param <X> the type of the independent variable (in) and the type of the dependent variable (output)
 * @see LinearInterpolationStrategy
 */
public class SingleTypedLinearInterpolationStrategy<X extends Comparable<? super X>>
        extends LinearInterpolationStrategy<X, X> implements SingleTypedInterpolationStrategy<X> {

    private static final long serialVersionUID = 1L;

    public SingleTypedLinearInterpolationStrategy(ExtendedField<X> field) {
        super(field, new Function<X, X>() {

            @Override
            public X apply(X input) {
                return input;
            }
        });
    }
}
