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

import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorInternals;

import com.google.common.base.Preconditions;

public class OngoingCompletion<S> {

    private final Tensor<S> tensor;

    OngoingCompletion(Tensor<S> tensor) {
        this.tensor = Preconditions.checkNotNull(tensor, "tensor must not be null");
    }

    public Tensor<S> with(Tensor<S> second) {
        return TensorStructurals.completeWith(tensor, second);
    }

    public Tensor<S> with(Shape shape, S value) {
        return with(TensorInternals.sameValues(shape, value));
    }

}
