// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.tensorbacked;

import java.io.Serializable;
import java.util.Set;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensorbuilder;

/**
 * An abstract class for classes that are backed by a tensor. The purpose of such classes is that they can be used for
 * calculations just in the same way (or at least in a similar way) as tensors themselves.
 * 
 * @author kfuchsbe
 * @param <E> the type of the elements of the tensor, which backs this class
 * @see Tensorbacked
 */
public abstract class AbstractTensorbacked<E> implements Tensorbacked<E>, Serializable {

    private static final long serialVersionUID = 1L;

    protected final Tensor<E> backingTensor;

    @SuppressWarnings("unchecked")
    public AbstractTensorbacked(Tensor<E> tensor) {
        TensorbackedInternals.verifyDimensions(this.getClass(), tensor);
        Set<Class<?>> annotatedDimensions = TensorbackedInternals.dimensionsOf(this.getClass());
        if (annotatedDimensions.equals(tensor.shape().dimensionSet())) {
            this.backingTensor = tensor;
        } else {
            this.backingTensor = copyWithDimensions(tensor, annotatedDimensions);
        }
    }

    private static <E> Tensor<E> copyWithDimensions(Tensor<E> tensor, Set<Class<?>> annotatedDimensions) {
        Tensorbuilder<E> builder = Tensorics.builder(annotatedDimensions);
        builder.putAll(tensor);
        builder.context(tensor.context());
        return builder.build();
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [backingTensor=" + backingTensor + "]";
    }

}