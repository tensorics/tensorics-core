/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.interpolation;

import org.tensorics.core.function.SingleTypedDiscreteFunction;

/**
 * An {@link InterpolationStrategy} for interpolating linearly {@link SingleTypedDiscreteFunction}
 * 
 * @author caguiler
 * @param <X> the type of the independent variable (in) and the type of the dependent variable (output)
 */
public interface SingleTypedInterpolationStrategy<V extends Comparable<? super V>> extends InterpolationStrategy<V> {

}
