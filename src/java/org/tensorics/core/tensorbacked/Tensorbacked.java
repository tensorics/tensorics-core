/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked;

import org.tensorics.core.tensor.Tensor;

/**
 * Interface for any object that will support Tensors and it will be able to transform itself into a tensor ie. for
 * mathematics calculations.
 * 
 * @author agorzaws
 * @param <T> Type of the tensor which backs this object.
 */
public interface Tensorbacked<T> {

    /**
     * @return a {@link Tensor} for given types.
     */
    Tensor<T> tensor();

    /**
     * @return an iterable to loop through the tensor entries
     */
    Iterable<Tensor.Entry<T>> entries();
}
