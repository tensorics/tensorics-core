package org.tensorics.core.tensor.lang;

import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensor.Entry;
import org.tensorics.core.tensor.operations.TensorInternals;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class OngoingOrderedFlattening<S, C extends Comparable<C>> {

    private final Tensor<S> tensor;
    private final Class<C> dimensionToFlatten;

    public OngoingOrderedFlattening(Tensor<S> tensor, Class<C> dimension) {
        super();
        this.tensor = tensor;
        this.dimensionToFlatten = dimension;
    }

    public Tensor<List<S>> intoTensorOfLists() {
        return Tensorics.fromMap(remainingDimensions(), Multimaps.asMap(intoListMultimap()));
    }

    private ListMultimap<Position, S> intoListMultimap() {
        ImmutableListMultimap.Builder<Position, S> builder = ImmutableListMultimap.builder();
        Tensor<Map<C, S>> maps = TensorInternals.mapOut(tensor).inDirectionOf(dimensionToFlatten);
        for (Entry<Map<C, S>> entry : maps.entrySet()) {
            Position newPosition = entry.getPosition();
            Map<C, S> map = entry.getValue();
            List<C> keys = new ArrayList<>(map.keySet());
            Collections.sort(keys);
            for (C key : keys) {
                builder.put(newPosition, map.get(key));
            }
        }
        return builder.build();
    }

    private SetView<Class<?>> remainingDimensions() {
        return Sets.difference(tensor.shape().dimensionSet(), singleton(dimensionToFlatten));
    }
}
