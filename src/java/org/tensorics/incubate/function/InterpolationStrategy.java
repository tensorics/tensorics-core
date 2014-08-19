/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

import java.io.Serializable;

/**
 * A strategy defines how to calculate output values of a function from the a finite set of discrete values (from a
 * discrete function).
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable (in)
 * @param <Y> the type of the dependent variable (output)
 */
public interface InterpolationStrategy<X extends Comparable<? super X>, Y> extends Serializable {

    Y interpolate(X xValue, DiscreteFunction<X, Y> function);

}
