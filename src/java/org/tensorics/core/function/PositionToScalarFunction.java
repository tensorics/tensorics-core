/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

class PositionToScalarFunction<S> implements Function<Position, S> {

    public PositionToScalarFunction(Tensor<S> tensor) {
        super();
        this.tensor = Preconditions.checkNotNull(tensor, "tensor must not be null");
    }

    private final Tensor<S> tensor;

    @Override
    public S apply(Position position) {
        return tensor.get(position);
    }

}