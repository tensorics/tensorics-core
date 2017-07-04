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

import java.util.Set;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Positions;
import org.tensorics.core.tensor.Positions.DimensionStripper;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorInternals;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class OngoingDimensionFlattening<S> {

    private final Tensor<S> tensor;
    private final Set<Class<?>> dimensionsToFlatten;

    public OngoingDimensionFlattening(Tensor<S> tensor, Set<Class<?>> dimensions) {
        super();
        this.tensor = tensor;
        this.dimensionsToFlatten = dimensions;
    }

    public Tensor<Set<S>> intoTensorOfSets() {
        return Tensorics.fromMap(remainingDimensions(), Multimaps.asMap(intoSetMultimap()));
    }

    private SetMultimap<Position, S> intoSetMultimap() {
        ImmutableSetMultimap.Builder<Position, S> builder = ImmutableSetMultimap.builder();
        DimensionStripper dimensionStripper = Positions.stripping(dimensionsToFlatten);
        for (java.util.Map.Entry<Position, S> entry : TensorInternals.mapFrom(tensor).entrySet()) {
            Position newPosition = dimensionStripper.apply(entry.getKey());
            builder.put(newPosition, entry.getValue());
        }
        return builder.build();
    }

    private SetView<Class<?>> remainingDimensions() {
        return Sets.difference(tensor.shape().dimensionSet(), dimensionsToFlatten);
    }

}
