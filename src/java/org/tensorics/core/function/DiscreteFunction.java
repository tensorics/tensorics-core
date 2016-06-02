/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import java.util.Set;

/**
 * A function which has only a discrete set of points (X/Y pairs). The {@link #apply(Object)} method will throw if an Y
 * value is requested for an unknown X.
 * 
 * @author kfuchsbe
 * @param <X>
 * @param <Y>
 */
public interface DiscreteFunction<X, Y> extends MathFunction<X, Y> {

    /**
     * @throws IllegalArgumentException in case the given x is not contained in the function
     */
    @Override
    Y apply(X input);
    
    
    Set<X> definedXValues();
    
}
