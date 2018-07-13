/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample;

import static java.util.stream.Collectors.toSet;
import static org.tensorics.core.tensor.Positions.strip;
import static org.tensorics.core.tensor.Positions.union;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensoric;

public class ResamplingStage<V, C> implements Tensoric<V> {

    private final Tensoric<V> nonresampled;
    private final SingleDimensionResampler<C, V> resampler;
    private final Class<C> dimension;
    private final Shape originalShape;

    public ResamplingStage(Shape originalShape, Tensoric<V> nonresampled, SingleDimensionResampler<C, V> resampler,
            Class<C> dimension) {
        this.originalShape = originalShape;
        this.nonresampled = nonresampled;
        this.resampler = resampler;
        this.dimension = dimension;
    }

    @Override
    public V get(Position position) {
        Set<C> supportingCoordinates = supportingCoordinates(position);
        C coordinate = coordinate(position);
        if (!resampler.canResample(supportingCoordinates, coordinate)) {
            throw new NoSuchElementException("Tensor cannot be resampled ad position " + position + ".");
        }
        return resampler.resample(supportingCoordinates, callbackFor(position), coordinate);
    }

    private Set<C> supportingCoordinates(Position position) {
        Position remainingPos = remaining(position);

        // @formatter:off
        return originalShape.coordinatesOfType(dimension).stream()
                .filter(c -> nonresampled.contains(union(remainingPos, Position.of(c))))
                .collect(toSet());
        // @formatter:on
    }

    private C coordinate(Position position) {
        return position.coordinateFor(dimension);
    }

    private Position remaining(Position position) {
        return strip(dimension).from(position);
    }

    private Function<C, V> callbackFor(Position position) {
        Position remainingPos = remaining(position);
        return c -> nonresampled.get(union(remainingPos, Position.of(c)));
    }

    @Override
    public boolean contains(Position position) {
        return resampler.canResample(supportingCoordinates(position), coordinate(position));
    }
}
