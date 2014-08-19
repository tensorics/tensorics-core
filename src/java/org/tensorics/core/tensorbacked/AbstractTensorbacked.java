/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked;

import org.tensorics.core.tensor.Tensor;

/**
 * An abstract class for classes that are backed by a tensor. The purpose of such classes is that they can be used for
 * calculations just in the same way (or at least in a similar way) as tensors themselves.
 * 
 * @author kfuchsbe
 * @param <E> the type of the elements of the tensor, which backs this class
 * @see Tensorbacked
 */
public abstract class AbstractTensorbacked<E> implements Tensorbacked<E> {

    protected final Tensor<E> backingTensor;

    public AbstractTensorbacked(Tensor<E> tensor) {
        this.backingTensor = tensor;
    }

    @Override
    public Tensor<E> tensor() {
        return this.backingTensor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((backingTensor == null) ? 0 : backingTensor.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractTensorbacked<?> other = (AbstractTensorbacked<?>) obj;
        if (backingTensor == null) {
            if (other.backingTensor != null) {
                return false;
            }
        } else if (!backingTensor.equals(other.backingTensor)) {
            return false;
        }
        return true;
    }

}