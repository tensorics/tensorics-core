/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.commons.lang;

/**
 * The top interface that defines all required common operations in the tensorics world.
 * 
 * @author agorzaws
 * @param <T> defines the type of the input/result main tensoric object
 * @param <V> defines the type of possible value interaction of the main tensoric object. In another words it is the
 *            tensor value.
 */
public interface OngoingOperation<T, V> {

    T plus(T right);

    T minus(T right);

    T elementTimes(T right);

    T elementTimesV(V value);

    T elementDividedBy(T right);

    T elementDividedByV(V value);

}
