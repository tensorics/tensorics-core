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

package org.tensorics.core.tensor.lang;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map.Entry;
import java.util.Set;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Preconditions;

public class OngoingCompletion<S> {

    private final Tensor<S> tensor;

    OngoingCompletion(Tensor<S> tensor) {
        this.tensor = Preconditions.checkNotNull(tensor, "tensor must not be null");
    }

    public Tensor<S> with(Tensor<S> second) {
        checkNotNull(second, "second tensor must not be null");
        checkArgument(second.shape().dimensionSet().equals(dimensions()),
                "Tensors do not have the same dimensions! Completion not supported!");
        Builder<S> builder = ImmutableTensor.builder(dimensions());
        builder.setTensorContext(tensor.context());
        for (Entry<Position, S> entry: second.asMap().entrySet()) {
            Position position = entry.getKey();
            if (tensor.shape().contains(position)) {
                builder.putAt(tensor.get(position), position);
            } else {
                builder.put(entry);
            }
        }
        return builder.build();
    }

    private Set<Class<?>> dimensions() {
        return tensor.shape().dimensionSet();
    }

}
