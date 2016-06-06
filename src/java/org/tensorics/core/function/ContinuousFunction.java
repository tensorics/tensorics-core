/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;


/**
 * This math funciton can return x values for (almost) any X values. The exact allowed x range depends on the
 * implementation.
 * 
 * @author kfuchsbe
 * @param <X>
 * @param <Y>
 */
public interface ContinuousFunction<X, Y> extends MathFunction<X, Y> {
    
    // as an idea
    // Range<X> allowedXRange();

}
