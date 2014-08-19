/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity;

import java.io.Serializable;

import com.google.common.base.Optional;

/**
 * A scalar value that additionally holds an error. The error type is the same as the value type.
 * 
 * @author kfuchsbe
 * @param <V> the type of the value of the scalar and therefore also the one of the error.
 */
public interface ErronousValue<V> extends Serializable {

    V value();

    /**
     * Retrieve the error of the scalar
     * 
     * @return the error
     */
    Optional<V> error();
}
