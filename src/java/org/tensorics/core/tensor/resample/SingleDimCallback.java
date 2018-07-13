/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample;

import java.util.Objects;
import java.util.function.Function;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Positions;

public class SingleDimCallback<C, V> implements Function<C, V> {

    private final Position completingPosition;
    private final Function<Position, V> callback;

    public SingleDimCallback(Position completingPosition, Function<Position, V> callback) {
        this.completingPosition = Objects.requireNonNull(completingPosition, "completingPosition must not be null");
        this.callback = Objects.requireNonNull(callback, "callback must not be null");
    }

    @Override
    public V apply(C c) {
        Position combined = Positions.union(completingPosition, Position.of(c));
        return callback.apply(combined);

    }

}
