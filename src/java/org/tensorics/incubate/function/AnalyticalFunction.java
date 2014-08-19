/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

/**
 * A function for which the output for any input value can be calculated from an (internal) analytical expression.
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable of the function (input)
 * @param <Y> the type of the dependent variable of the function (output)
 */
public interface AnalyticalFunction<X extends Comparable<? super X>, Y> extends ContinuousFunction<X, Y> {

    String toText();

}
