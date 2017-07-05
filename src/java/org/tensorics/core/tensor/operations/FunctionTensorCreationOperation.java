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

import java.util.function.Function;

import org.tensorics.core.math.operations.CreationOperation;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * Uses the given function from a position to the tensor to create the values of the tensor.
 * 
 * @author kaifox
 * @param <V> the type of the elements of the tensor to be created
 */
public class FunctionTensorCreationOperation<V> implements CreationOperation<Tensor<V>> {

    private final Shape shape;
    private final Function<Position, V> function;

    public FunctionTensorCreationOperation(Shape shape, Function<Position, V> function) {
        super();
        this.shape = shape;
        this.function = function;
    }

    @Override
    public Tensor<V> perform() {
        Builder<V> builder = ImmutableTensor.builder(shape.dimensionSet());
        for (Position position : shape.positionSet()) {
            builder.put(position, function.apply(position));
        }
        return builder.build();
    }

}
