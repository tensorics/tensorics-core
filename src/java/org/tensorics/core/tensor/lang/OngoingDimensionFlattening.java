/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import java.util.List;
import java.util.Set;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Positions;
import org.tensorics.core.tensor.Positions.DimensionStripper;
import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
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

    public Tensor<List<S>> intoTensorOfLists() {
        return Tensorics.fromMap(remainingDimensions(), Multimaps.asMap(intoListMultimap()));
    }

    private ListMultimap<Position, S> intoListMultimap() {
        ImmutableListMultimap.Builder<Position, S> builder = ImmutableListMultimap.builder();
        DimensionStripper dimensionStripper = Positions.stripping(dimensionsToFlatten);
        for (java.util.Map.Entry<Position, S> entry : tensor.asMap().entrySet()) {
            Position newPosition = dimensionStripper.apply(entry.getKey());
            builder.put(newPosition, entry.getValue());
        }
        return builder.build();
    }

    private SetView<Class<?>> remainingDimensions() {
        return Sets.difference(tensor.shape().dimensionSet(), dimensionsToFlatten);
    }

}
