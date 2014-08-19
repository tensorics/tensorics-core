/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

/**
 * A callback that can be passed to a builder so that it can check any new scalar added if it is conform to certain
 * criteria.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar to check
 */
public interface VerificationCallback<S> {

    /**
     * This method might be overridden by a subclass, to verify the consistency of a scalar. It may throw, if the entry
     * cannot be put to the tensor.
     * 
     * @param scalar the scalar to verify
     * @throws IllegalArgumentException if the scalar is not allowed to be put into the tensor under construction.
     */
    void verify(S scalar);

}