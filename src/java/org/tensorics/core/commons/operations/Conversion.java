/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.commons.operations;

/**
 * An operation that converts one type of object into another.
 * 
 * @author kfuchsbe
 * @param <T> the type of the object which shall be converted
 * @param <R> the return type, i.e. the type into which the object shall be converted
 */
public interface Conversion<T, R> {

    /**
     * Has to implement the conversion logic to convert the given object into an object of type R.
     * 
     * @param object the object to convert
     * @return an object of the correct return type.
     */
    R perform(T object);

}
