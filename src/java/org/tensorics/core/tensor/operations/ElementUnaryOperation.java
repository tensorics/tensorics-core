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
public class ElementUnaryOperation<V> implements UnaryOperation<Tensor<V>> {

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
            builder.at(position).put(elementOperation.perform(tensor.get(position)));
        }
        return builder.build();
    }

}
