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
package org.tensorics.core.tensor.operations;

import java.io.Serializable;

import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * Operates on one tensor and produces a new tensor of the same shape by applying a unary operation on each value of the
 * tensor. Thus it is uniquely defined by the operation on the elements.
 *
 * @author kfuchsbe
 * @param <V> the type of the value of the tensor
 */
public class ElementUnaryOperation<V> implements UnaryOperation<Tensor<V>>, Serializable {
    private static final long serialVersionUID = 1L;

    private final UnaryOperation<V> elementOperation;

    public ElementUnaryOperation(UnaryOperation<V> elementOperation) {
        super();
        this.elementOperation = elementOperation;
    }

    @Override
    public Tensor<V> perform(Tensor<V> tensor) {
        Shape shape = tensor.shape();
        Builder<V> builder = ImmutableTensor.builder(shape.dimensionSet());
        for (Position position : shape.positionSet()) {
            builder.put(position, elementOperation.perform(tensor.get(position)));
        }
        return builder.build();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((elementOperation == null) ? 0 : elementOperation.hashCode());
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
        ElementUnaryOperation<?> other = (ElementUnaryOperation<?>) obj;
        if (elementOperation == null) {
            if (other.elementOperation != null) {
                return false;
            }
        } else if (!elementOperation.equals(other.elementOperation)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ElementUnaryOperation [elementOperation=" + elementOperation + "]";
    }

}
