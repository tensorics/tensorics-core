/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import static org.tensorics.core.tensor.Positions.strip;
import static org.tensorics.core.tensor.Positions.union;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensoric;

/**
 * A tensoric object, which is created by applying one resampler on a certain dimension onto a previous tensoric object,
 * which e.g. by itself can be a resampled one or a tensor.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the tensoric objects
 * @param <C> the type of the coordinates to resample
 */
public class ResamplingStage<V, C> implements Tensoric<V> {

    private final Tensoric<V> nonresampled;
    private final SingleDimensionResampler<C, V> resampler;
    private final Class<C> dimension;
    private final Shape originalShape;

    /**
     * Constructs a new resampling stage, derived from a previous stage by using a resampler and a dimension.
     * 
     * @param supportingShape the shape of the original tensor. This is passed on through all the stages, as the
     *            supporting points have to be extracted from it during each stage.
     * @param previousStage the stage, representing the previous resampling (or the original tensor)
     * @param resampler the resampler to use for the given dimension
     * @param dimension the the dimension which is resampled by this stage
     */
    public ResamplingStage(Shape supportingShape, Tensoric<V> previousStage, SingleDimensionResampler<C, V> resampler,
            Class<C> dimension) {
        this.originalShape = requireNonNull(supportingShape, "supportingShape must not be null");
        this.nonresampled = requireNonNull(previousStage, "previousStage must not be null");
        this.resampler = requireNonNull(resampler, "resampler must not be null");
        this.dimension = requireNonNull(dimension, "dimension must not be null");
    }

    @Override
    public V get(Position position) {
        Set<C> supportingCoordinates = supportingCoordinates(position);
        C coordinate = coordinate(position);
        if (!resampler.canResample(supportingCoordinates, coordinate)) {
            throw new NoSuchElementException("Tensor cannot be resampled at " + position + ".");
        }
        return resampler.resample(supportingCoordinates, callbackFor(position), coordinate);
    }

    @Override
    public boolean contains(Position position) {
        return resampler.canResample(supportingCoordinates(position), coordinate(position));
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

}
