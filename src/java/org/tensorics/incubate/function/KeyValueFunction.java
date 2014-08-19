/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

/**
 * Represents the most general function that basically maps x-values to y values. One could also see it as a function
 * with exactly ONE variable (x). The exact behavior of the {@link #getY(Comparable)} method depends on the
 * implementations.
 * 
 * @author agorzaws
 * @param <X> type of arguments
 * @param <Y> type of values
 */
public interface KeyValueFunction<X extends Comparable<? super X>, Y> {

    /**
     * @param xValue argument of type <X>
     * @return value of type <Y>
     */
    Y getY(X xValue);
}
