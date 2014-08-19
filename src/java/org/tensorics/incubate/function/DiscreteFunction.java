/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

import java.io.Serializable;
import java.util.List;

/**
 * THe following conditions are guaranteed:
 * <ul>
 * <li>The x-values are ordered by their natural ordering
 * <li>the lists returned by {@link #getXs()} and {@link #getYs()} are of the same length.
 * <ul>
 * <p>
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable of the function
 * @param <Y> the type of the dependent variable of the function
 */
public interface DiscreteFunction<X extends Comparable<? super X>, Y> extends KeyValueFunction<X, Y>, Serializable {

    String getName();

    List<X> getXs();

    List<Y> getYs();

    List<Y> getYsErr();
}
