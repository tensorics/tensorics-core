/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import org.tensorics.core.tensor.Position;

import com.google.common.base.Function;

public class CoordinateToPositionFunction<C> implements Function<C, Position> {

    @Override
    public Position apply(C coordinate) {
        return Position.of(coordinate);
    }

}
