/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

/**
 * A function for which the output can be retrieved for any input value.
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable of the function (input)
 * @param <Y> the type of the dependent variable of the function (output)
 */
public interface ContinuousFunction<X extends Comparable<? super X>, Y> extends KeyValueFunction<X, Y> {

    /* only marker at the moment */

}
