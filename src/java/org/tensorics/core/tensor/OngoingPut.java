/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;


/**
 * Inner Builder object to manage between positions and values.
 * 
 * @author agorzaws
 * @param <T> type of values
 */
public final class OngoingPut<T> {
    private final Position position;
    private final TensorBuilder<T> tensorBuilder;

    OngoingPut(Position position, TensorBuilder<T> tensorBuilder) {
        this.position = position;
        this.tensorBuilder = tensorBuilder;
    }

    /**
     * Allows to assign values to previously set position (set of coordinates)
     * 
     * @param entryValue as value to set
     */
    public void put(T entryValue) {
        this.tensorBuilder.putValueAt(entryValue, this.position);
    }

}