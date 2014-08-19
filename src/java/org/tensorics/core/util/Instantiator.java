/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

/**
 * Provides a way to create instances of a certain type of objects, which need one argument as their input.
 * 
 * @author kfuchsbe
 * @param <A> the type of the argument required to create an instance
 * @param <T> the type of the instance to be created.
 */
public interface Instantiator<A, T> {

    /**
     * Creates an instance, using the given argument.
     * 
     * @param argument the argument used to create the new instance
     * @return an instance of the respective type
     */
    <A1 extends A> T create(A1 argument);

}